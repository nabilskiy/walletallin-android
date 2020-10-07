package com.example.coinsliberty.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.dialogs.DialogAccept
import com.example.coinsliberty.dialogs.DialogError
import com.example.coinsliberty.dialogs.DialogQrCode
import kotlinx.android.synthetic.main.activity_demo.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnDialogAccept.setOnClickListener {
            DialogAccept.newInstance("this balance", "this link")
                .show(supportFragmentManager, DialogAccept.TAG)
        }
        btnDialogError.setOnClickListener {
            DialogError.newInstance("this tittle")
                .show(supportFragmentManager, DialogError.TAG)
        }
        btnDialogQrCode.setOnClickListener {
            DialogQrCode.newInstance("this tittle", "1FThvklkdfG678Jhghjhjg^7hfH784Dfg")
                .show(supportFragmentManager, DialogQrCode.TAG)
        }
    }
}