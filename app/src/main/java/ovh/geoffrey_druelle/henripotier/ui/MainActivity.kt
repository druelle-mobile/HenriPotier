package ovh.geoffrey_druelle.henripotier.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ovh.geoffrey_druelle.henripotier.R

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
    }
}
