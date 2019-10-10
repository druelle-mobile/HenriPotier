package ovh.geoffrey_druelle.henripotier

import android.app.Application
import android.content.Context

private val TAG = HenriPotierApplication::class.java.name

class HenriPotierApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }
}