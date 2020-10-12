package com.example.coinsliberty.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.QrCodeDialog
import com.example.coinsliberty.dialogs.SendDialog
import kotlinx.android.synthetic.main.activity_demo.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnTest.setOnClickListener {
            SendDialog.newInstance(
                "SEnD ETH",
                " 16azaQHPThoQcf14o2XqmbG",
                "0.25947531",
                "2671.53",
                "ETC",
                "USD"
            )
                .show(supportFragmentManager, SendDialog.TAG)
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