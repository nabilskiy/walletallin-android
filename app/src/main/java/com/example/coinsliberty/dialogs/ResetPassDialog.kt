package com.example.coinsliberty.dialogs


import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_reset_pass.*


class ResetPassDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_reset_pass
    override val viewModel: StubViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { dismiss() }
        btnUpdate.setOnClickListener { listener?.invoke(true) }
    }


    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = ResetPassDialog::class.java.name
        fun newInstance(): ResetPassDialog {
            val fragment = ResetPassDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}