package ovh.geoffrey_druelle.henripotier.ui.books

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.databinding.BooksFragmentBinding

class BooksFragment : Fragment() {

    companion object {
        fun newInstance() = BooksFragment()
    }

    private lateinit var viewModel: BooksViewModel
    private lateinit var binding: BooksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.books_fragment,
            container,
            false
        )

        viewModel = getViewModel()
        binding.datacontext = viewModel
        binding.lifecycleOwner = this
        
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.see_cart -> {
                navigateToCart()
                true
            }
            R.id.about -> {
                navigateToInformations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToInformations() {
        val action = R.id.action_booksFragment_to_informationsFragment
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateToCart() {
        val action = R.id.action_booksFragment_to_cartFragment
        NavHostFragment.findNavController(this).navigate(action)
    }
}
