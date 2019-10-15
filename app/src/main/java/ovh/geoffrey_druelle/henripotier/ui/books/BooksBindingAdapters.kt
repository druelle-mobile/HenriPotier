package ovh.geoffrey_druelle.henripotier.ui.books

import android.text.TextUtils
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ovh.geoffrey_druelle.henripotier.data.model.Book

@BindingAdapter(value = ["items","viewModel"], requireAll = false)
fun setRecyclerViewSource(
    recyclerView: RecyclerView,
    list: List<Book>,
    viewModel: BooksViewModel
) {
    recyclerView.adapter?.run {
        if (this is BooksAdapter) {
            this.books = list
            this.notifyDataSetChanged()
        }
    } ?: run {
        BooksAdapter(list, viewModel).apply {
            recyclerView.adapter = this
        }
    }
}

@BindingAdapter("cover")
fun setCover(imageView: ImageView, url: String){
    Picasso.get().load(url).resize(200,300).onlyScaleDown().into(imageView)
}