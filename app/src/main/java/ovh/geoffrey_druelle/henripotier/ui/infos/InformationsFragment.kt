package ovh.geoffrey_druelle.henripotier.ui.infos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.databinding.InformationsFragmentBinding
import ovh.geoffrey_druelle.henripotier.ui.MainActivity
import ovh.geoffrey_druelle.henripotier.ui.MainActivity.Companion.isDarkMode
import ovh.geoffrey_druelle.henripotier.utils.GD_URL


class InformationsFragment : Fragment() {

    companion object {
        fun newInstance() = InformationsFragment()
    }

    private lateinit var viewModel: InformationsViewModel
    private lateinit var binding: InformationsFragmentBinding

    private lateinit var buttonTheme: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.informations_fragment,
            container,
            false
        )
        viewModel = getViewModel()
        binding.datacontext = viewModel
        binding.lifecycleOwner = this

        viewModel.isLogoClicked.observe(this, Observer { isLogoClicked ->
            if (isLogoClicked){
                navigateToGDWebsite()
                viewModel.logoClicked()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonTheme = view.findViewById(R.id.button_theme)
        buttonTheme.setOnCheckedChangeListener { _, b ->
            isDarkMode = b
            switchTheme()
        }
    }

    private fun switchTheme() {
        if (isDarkMode) {
            MainActivity.instance.delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        } else {
            MainActivity.instance.delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }
    }


    private fun navigateToGDWebsite() {
        val dialog = AlertDialog.Builder(MainActivity.instance)
            .setTitle("Ouvrir le lien ?")
            .setMessage("En acceptant, vous allez ouvrir votre navigateur internet et ouvrir la page https://geoffrey-druelle.ovh")
            .setPositiveButton("Continuer"){ dialogInterface, _ ->
                dialogInterface.cancel()
                openWebBrowserIntent()
            }
            .setNegativeButton("Annuler"){ dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun openWebBrowserIntent() {
        val uri: Uri = Uri.parse(GD_URL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(activity?.packageManager!!) != null) {
            startActivity(intent)
        }
    }
}
