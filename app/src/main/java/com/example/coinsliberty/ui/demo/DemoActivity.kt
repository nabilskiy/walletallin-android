package com.example.coinsliberty.ui.demo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog


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