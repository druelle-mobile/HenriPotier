package ovh.geoffrey_druelle.henripotier.ui.bookDetails

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_book_details.*
import kotlinx.android.synthetic.main.layout_book_details.view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.databinding.LayoutBookDetailsBinding

class BookDetailsFragment : DialogFragment() {

    private lateinit var book: Book

    companion object {
        private var SELECTED_BOOK = "SELECTED_BOOK"
        private const val TAG = "BookDetailsFragment"

        private fun newInstance(book: Book) : BookDetailsFragment {
            val bookDetailsFragment =
                BookDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(SELECTED_BOOK, book)
            bookDetailsFragment.arguments = arguments
            return bookDetailsFragment
        }

        fun show(
            fragmentTransaction: FragmentTransaction,
            book: Book
        ): BookDetailsFragment {
            val dialog =
                newInstance(
                    book
                )
            dialog.show(fragmentTransaction,
                TAG
            )
            return dialog
        }
    }

    private lateinit var customView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        book = arguments?.getParcelable(SELECTED_BOOK)!!
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.layout_book_details, null)
        customView = view

        Picasso.get().load(book.cover).resize(400,500).onlyScaleDown().into(view.imageview_cover)
        view.textview_price.text = String.format(resources.getString(R.string.price_in_euros), book.price)
        view.textview_summary.text = TextUtils.join("\n\n", book.synopsis)

        val builder = AlertDialog.Builder(context!!)
            .setTitle(book.title)
            .setView(view)
            .setNegativeButton(getString(R.string.quit)) { _, _ ->
                dialog?.cancel()
            }

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}