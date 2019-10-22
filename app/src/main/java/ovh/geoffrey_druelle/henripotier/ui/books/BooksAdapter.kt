package ovh.geoffrey_druelle.henripotier.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.databinding.ItemBookBinding
import ovh.geoffrey_druelle.henripotier.ui.MainActivity
import ovh.geoffrey_druelle.henripotier.ui.bookDetails.BookDetailsFragment


class BooksAdapter(var books: List<Book> = listOf(), private val booksViewModel: BooksViewModel) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_book,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = books[position]
        holder.binding.itemBook = book
        holder.binding.datacontext = booksViewModel
        holder.itemView.setOnClickListener {
            openDialogDetailsFragment(book)
        }
    }

    private fun openDialogDetailsFragment(book: Book) {
        val fragmentTransaction = MainActivity.instance.supportFragmentManager.beginTransaction()
        BookDetailsFragment.show(fragmentTransaction, book)
    }

    inner class BooksViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemBookBinding = DataBindingUtil.bind(view)!!
    }
}