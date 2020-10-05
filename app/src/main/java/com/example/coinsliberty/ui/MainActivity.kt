package com.example.coinsliberty.ui

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTransparentLightStatusBar()
        setupFullScreen()


    }
}