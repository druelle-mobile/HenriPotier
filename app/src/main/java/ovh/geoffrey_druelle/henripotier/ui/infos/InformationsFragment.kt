package ovh.geoffrey_druelle.henripotier.ui.infos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ovh.geoffrey_druelle.henripotier.R

class InformationsFragment : Fragment() {

    companion object {
        fun newInstance() = InformationsFragment()
    }

    private lateinit var viewModel: InformationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.informations_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InformationsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
