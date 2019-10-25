package ovh.geoffrey_druelle.henripotier.ui.books

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.databinding.BooksFragmentBinding

class BooksFragment : Fragment() {

    companion object {
        fun newInstance() = BooksFragment()
    }

    private lateinit var booksViewModel: BooksViewModel
    private lateinit var booksBinding: BooksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        booksBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.books_fragment,
            container,
            false
        )

        booksViewModel = getViewModel()
        booksBinding.datacontext = booksViewModel
        booksBinding.lifecycleOwner = this

        booksViewModel.bookAdded.observe(this, Observer { bookAdded ->
            if (bookAdded){
                Snackbar.make(requireView(),R.string.book_added,Snackbar.LENGTH_SHORT).show()
                booksViewModel.bookAdded()
            }
        })

        return booksBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.see_cart -> {
                navigateToCart()
                true
            }
            R.id.auth -> {
                navigateToAuthentication()
                true
            }
            R.id.about -> {
                navigateToInformations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAuthentication() {
        val action = R.id.action_booksFragment_to_loginFragment
        NavHostFragment.findNavController(this).navigate(action)    }

    private fun navigateToInformations() {
        val action = R.id.action_booksFragment_to_informationsFragment
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateToCart() {
        val action = R.id.action_booksFragment_to_cartFragment
        NavHostFragment.findNavController(this).navigate(action)
    }

}
