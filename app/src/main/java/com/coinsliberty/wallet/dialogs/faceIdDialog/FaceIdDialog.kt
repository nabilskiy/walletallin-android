package com.coinsliberty.wallet.dialogs.faceIdDialog

import android.os.Bundle
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_face_id.*
import org.koin.android.viewmodel.ext.android.viewModel


class FaceIdDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_face_id
    override val viewModel: FaceIdViewModel by viewModel()

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
        val TAG: String = FaceIdDialog::class.java.name
        fun newInstance(): FaceIdDialog {
            val fragment = FaceIdDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}