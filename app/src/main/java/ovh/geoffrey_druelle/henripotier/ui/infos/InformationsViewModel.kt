package ovh.geoffrey_druelle.henripotier.ui.infos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication

class InformationsViewModel : ViewModel() {

    var version: String = HenriPotierApplication.instance.getVersionNumber()

    private val _isLogoClicked = MutableLiveData<Boolean>()
    val isLogoClicked: LiveData<Boolean>
        get() = _isLogoClicked

    fun openGDWebsite() {
        _isLogoClicked.value = true
    }

    fun logoClicked() {
        _isLogoClicked.value = false
    }
}
