package com.example.coinsliberty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen
import kotlinx.android.synthetic.main.activity_bottom.*

class BottomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        setTransparentLightStatusBar()
        setupFullScreen()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wallet -> {
                    Navigation.findNavController(frameLayout.requireView()).navigate(R.id.action_global_my_wallet)
                    true
                }
                R.id.exchange -> {
                    Navigation.findNavController(frameLayout.requireView()).navigate(R.id.action_global_exchange)
                    true
                }
                R.id.setting -> {
                    Navigation.findNavController(frameLayout.requireView()).navigate(R.id.action_global_settings)
                    true
                }
                else -> false
            }
        }
    }

    private fun setContent(content: String) {
        setTitle(content)
    }

}