package ovh.geoffrey_druelle.henripotier.ui.books

import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ovh.geoffrey_druelle.henripotier.base.BaseViewModel
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.network.HPApi
import ovh.geoffrey_druelle.henripotier.utils.LogsUtils

private val TAG = BooksViewModel::class.java.name

class BooksViewModel(private val api: HPApi) : BaseViewModel() {

    private lateinit var subscription: Disposable

    private val _books: MutableLiveData<List<Book>> = MutableLiveData(listOf())
    val books: LiveData<List<Book>>
        get() = _books

    init {
        loadBooksList()
    }

    private fun loadBooksList() {
        subscription = api.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it
            },{
                LogsUtils.e("$TAG - Error : ", it as Exception)
            })
    }

//    fun getOnItemClickedFunction(): Consumer<Book> {
////        return Consumer {  }
////    }

    fun isInCart(book: Book): Boolean {
        return false
    }

    fun addToCart(book: Book){

    }

    fun removeFromCart(book: Book){

    }
}
