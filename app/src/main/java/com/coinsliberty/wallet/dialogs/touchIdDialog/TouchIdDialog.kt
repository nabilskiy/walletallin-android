package com.coinsliberty.wallet.dialogs.touchIdDialog


import android.os.Bundle
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import kotlinx.android.synthetic.main.dialog_face_id.*
import org.koin.android.viewmodel.ext.android.viewModel


class TouchIdDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_touch_id
    override val viewModel: TouchIdViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvOrUseMPin.setOnClickListener {
            //// go to
        }
    }


    companion object {
        val TAG: String = TouchIdDialog::class.java.name
        fun newInstance(): TouchIdDialog {
            val fragment = TouchIdDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}