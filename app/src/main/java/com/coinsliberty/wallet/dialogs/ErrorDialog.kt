package com.coinsliberty.wallet.dialogs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_error.*

private const val keyBundle = "title"

class ErrorDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_error
    override val viewModel: StubViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTextError.text = arguments?.getString(keyBundle)
        ivClose.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = ErrorDialog::class.java.name
        fun newInstance(title: String): DialogFragment {
            val fragment = ErrorDialog()
            val bundle = bundleOf(keyBundle to title)
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogRedBG)
            return fragment
        }
    }
}