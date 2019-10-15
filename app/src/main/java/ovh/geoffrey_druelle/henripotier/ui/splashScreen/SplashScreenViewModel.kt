package ovh.geoffrey_druelle.henripotier.ui.splashScreen

import ovh.geoffrey_druelle.henripotier.HenriPotierApplication
import ovh.geoffrey_druelle.henripotier.base.BaseViewModel

class SplashScreenViewModel : BaseViewModel() {

    var version: String = HenriPotierApplication.instance.getVersionNumber()
}
