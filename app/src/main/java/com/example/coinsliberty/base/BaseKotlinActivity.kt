package com.example.coinsliberty.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.coinsliberty.utils.extensions.bindDataTo

abstract class BaseKotlinActivity : FragmentActivity() {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress) {  }
        bindDataTo(viewModel.onEndProgress) {  }
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}