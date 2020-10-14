package com.example.coinsliberty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bottom.*

class BottomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wallet -> {
                    true
                }
                R.id.exchange -> {
                    true
                }
                R.id.setting -> {
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