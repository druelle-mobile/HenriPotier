package ovh.geoffrey_druelle.henripotier.ui.splashScreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.databinding.SplashScreenFragmentBinding


private val TAG = SplashScreenFragment::class.java.name

class SplashScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SplashScreenFragment()
    }

    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: SplashScreenFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.splash_screen_fragment,
            container,
            false
        )
        viewModel = getViewModel()
        binding.datacontext = viewModel
        binding.lifecycleOwner = this

        Handler().postDelayed({
            navigateToBooksFragment()
        }, 1000)

        return binding.root
    }

    private fun navigateToBooksFragment() {
        val action = R.id.action_splashScreenFragment_to_booksFragment
        NavHostFragment.findNavController(this).navigate(action)
    }
}
