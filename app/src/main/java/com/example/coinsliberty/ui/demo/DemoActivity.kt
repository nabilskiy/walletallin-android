package com.example.coinsliberty.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.dialogs.*
import kotlinx.android.synthetic.main.activity_demo.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnTest.setOnClickListener {
            ResetPassDialog.newInstance()
                .show(supportFragmentManager, ResetPassDialog.TAG)
        }
//        btnDialogError.setOnClickListener {
//            ErrorDialog.newInstance("this tittle")
//                .show(supportFragmentManager, ErrorDialog.TAG)
//        }
//        btnDialogQrCode.setOnClickListener {
//            QrCodeDialog.newInstance("this tittle", "1FThvklkdfG678Jhghjhjg^7hfH784Dfg")
//                .show(supportFragmentManager, QrCodeDialog.TAG)
//        }
    }
}