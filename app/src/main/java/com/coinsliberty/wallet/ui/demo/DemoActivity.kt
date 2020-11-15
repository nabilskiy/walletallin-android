package com.coinsliberty.wallet.ui.demo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.ui.dialogs.ChangeLanguageDialog


class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        ChangeLanguageDialog.newInstance(R.drawable.ic_unitedstates).apply {
            initListeners {
                Log.e("!!!", it.toString())
                dismiss()
            }
        }.show(supportFragmentManager, ChangeLanguageDialog.TAG)

    }
}