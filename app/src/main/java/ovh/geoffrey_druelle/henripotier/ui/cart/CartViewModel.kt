package ovh.geoffrey_druelle.henripotier.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication.Companion.appContext
import ovh.geoffrey_druelle.henripotier.base.BaseViewModel
import ovh.geoffrey_druelle.henripotier.data.database.HPDatabase
import ovh.geoffrey_druelle.henripotier.data.model.Cart
import ovh.geoffrey_druelle.henripotier.data.model.Offer
import ovh.geoffrey_druelle.henripotier.data.model.Offers
import ovh.geoffrey_druelle.henripotier.network.HPApi
import ovh.geoffrey_druelle.henripotier.utils.LogsUtils
import java.util.concurrent.Executors
import kotlin.math.max

private val TAG = CartViewModel::class.java.name

class CartViewModel(private val api: HPApi) : BaseViewModel() {

    private lateinit var cartSubscription: Disposable
    private lateinit var offersSubscription: Disposable
    private var executor = Executors.newSingleThreadExecutor()
    private val db = HPDatabase.getInstance(appContext)

    private var reduces = 0
    private lateinit var offers: Offers

    private var _cart: MutableLiveData<List<Cart>> = MutableLiveData(listOf())
    val cart: LiveData<List<Cart>>
        get() = _cart

    private val _bookRemoved = MutableLiveData<Boolean>()
    val bookRemoved: LiveData<Boolean>
        get() = _bookRemoved

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean>
        get() = _isEmpty

    private val _priceBeforeReduces: MutableLiveData<Int> = MutableLiveData(0)
    val priceBeforeReduces: LiveData<Int>
        get() = _priceBeforeReduces

    private val _totalReduces: MutableLiveData<Int> = MutableLiveData(0)
    val totalReduces: LiveData<Int>
        get() = _totalReduces

    private val _priceAfterReduces: MutableLiveData<Int> = MutableLiveData(0)
    val priceAfterReduces: LiveData<Int>
        get() = _priceAfterReduces

    init {
        executor.execute(this::loadCartItems)
    }

    private fun loadCartItems() {
        cartSubscription = db.cartDao().getAllBooksInCart()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _cart.value = it
                initPriceBeforeReduces()
                isCartEmpty()
            }, {
                LogsUtils.e("$TAG - Error : ", it as Exception)
            })
    }

    private fun isCartEmpty() {
        _isEmpty.value = _cart.value?.isNotEmpty()
    }

    private fun initPriceBeforeReduces() {
        var startPrice = 0
        for (item in cart.value!!) startPrice += item.price
        _priceBeforeReduces.postValue(startPrice)

        loadOffers()
    }

    private fun loadOffers() {
        offersSubscription = api.getOffers(_cart.value!!.joinToString { it.isbn })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                offers = it
                reduces = 0

                var bestReduce = 0
                for (offer in offers.offers) {
                    bestReduce = max(
                        bestReduce,
                        parseOffer(offer)
                    )
                }
                _totalReduces.value = bestReduce

                fixPriceAfterReduces()
            }, {
                LogsUtils.e("${TAG} - Error : ", it as Exception)
            })
    }

    private fun parseOffer(offer: Offer): Int {
        val reduce: Int

        when (offer.type) {
            "percentage" -> reduce = percentageValue(offer.value, _priceBeforeReduces)
            "minus" -> reduce = offer.value
            "slice" -> reduce = sliceValue(offer, _priceBeforeReduces)
            else -> reduce = 0
        }
        return reduce
    }

    private fun sliceValue(offer: Offer, _priceBeforeReduces: MutableLiveData<Int>): Int {
        return (_priceBeforeReduces.value!!.div(offer.sliceValue)) * offer.value
    }

    private fun percentageValue(value: Int, _priceBeforeReduces: MutableLiveData<Int>): Int {
        return _priceBeforeReduces.value?.times(value)!!.div(100)
    }

    private fun fixPriceAfterReduces() {
        val finalPrice = _priceBeforeReduces.value!!.minus(_totalReduces.value!!)
        _priceAfterReduces.postValue(finalPrice)
    }

    fun removeFromCart(cartItem: Cart) {
        executor.execute {
            db.cartDao().delete(cartItem)
        }
        _bookRemoved.value = true
        loadCartItems()
    }

    fun bookRemoved() {
        _bookRemoved.value = false
    }
}
