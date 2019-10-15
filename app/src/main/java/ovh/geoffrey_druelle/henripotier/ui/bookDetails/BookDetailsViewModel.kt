package ovh.geoffrey_druelle.henripotier.ui.bookDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ovh.geoffrey_druelle.henripotier.base.BaseViewModel
import ovh.geoffrey_druelle.henripotier.data.model.Book


private val TAG = BookDetailsViewModel::class.java.name

class BookDetailsViewModel : BaseViewModel() {
    private val _book: MutableLiveData<Book> = MutableLiveData()
    val book: LiveData<Book>
        get() = _book
}