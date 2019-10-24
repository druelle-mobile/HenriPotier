package ovh.geoffrey_druelle.henripotier.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.databinding.CartFragmentBinding


class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartBinding: CartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

    cartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.cart_fragment,
            container,
            false
        )

        cartViewModel = getViewModel()
        cartBinding.datacontext = cartViewModel
        cartBinding.lifecycleOwner = this

        cartViewModel.bookRemoved.observe(this, Observer { bookRemoved ->
            if (bookRemoved){
                Snackbar.make(requireView(), R.string.book_removed, Snackbar.LENGTH_SHORT).show()
                cartViewModel.bookRemoved()
            }
        })

        return cartBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home ->{
                activity?.onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
