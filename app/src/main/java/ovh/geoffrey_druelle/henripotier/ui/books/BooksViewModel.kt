package ovh.geoffrey_druelle.henripotier.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication.Companion.appContext
import ovh.geoffrey_druelle.henripotier.base.BaseViewModel
import ovh.geoffrey_druelle.henripotier.data.database.HPDatabase
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.data.model.Cart
import ovh.geoffrey_druelle.henripotier.network.HPApi
import ovh.geoffrey_druelle.henripotier.utils.LogsUtils
import java.util.concurrent.Executors

private val TAG = BooksViewModel::class.java.name

class BooksViewModel(private val api: HPApi) : BaseViewModel() {

    private lateinit var subscription: Disposable
    private var executor = Executors.newSingleThreadExecutor()
    private val db = HPDatabase.getInstance(appContext)

    private val _books: MutableLiveData<List<Book>> = MutableLiveData(listOf())
    val books: LiveData<List<Book>>
        get() = _books

    private val _bookAdded = MutableLiveData<Boolean>()
    val bookAdded: LiveData<Boolean>
        get() = _bookAdded

    init {
        loadBooksList()
    }

    private fun loadBooksList() {
        subscription = api.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it
            }, {
                LogsUtils.e("$TAG - Error : ", it as Exception)
            })
    }

    fun addToCart(book: Book) {
        executor.execute {
            db.cartDao().insert(Cart.convertToCartObject(book))
        }
        _bookAdded.value = true
    }

    fun bookAdded(){
        _bookAdded.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}
