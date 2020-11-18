package com.coinsliberty.wallet.ui.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.dialogs.faceIdDialog.FaceIdDialog
import com.coinsliberty.wallet.dialogs.touchIdDialog.TouchIdDialog
import com.coinsliberty.wallet.ui.dialogs.ChangeLanguageDialog
import kotlinx.android.synthetic.main.activity_demo.*


class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

//        btnTestFace.setOnClickListener {
//            FaceIdDialog.newInstance().apply {
//            }.show(supportFragmentManager, FaceIdDialog.TAG)
//        }
//
//
//        btnTestTouch.setOnClickListener {
//            TouchIdDialog.newInstance().apply {
//            }.show(supportFragmentManager, TouchIdDialog.TAG)
//        }

    }
}