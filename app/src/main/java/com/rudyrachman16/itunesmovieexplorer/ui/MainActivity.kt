package com.rudyrachman16.itunesmovieexplorer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.rudyrachman16.itunesmovieexplorer.databinding.ActivityMainBinding
import com.rudyrachman16.itunesmovieexplorer.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        val splashScreen = installSplashScreen()

        setContentView(bind.root)

        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            delay(800)
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }
    }
}