package com.example.coinsliberty.dialogs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_accept.*

class DialogAccept : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_accept
    override val viewModel: StubViewModel by viewModel()

    private val keyBundleBalance = "balance"
    private val keyBundleLink = "link"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBalance.text = arguments?.getString(keyBundleBalance)
        tvLink.text = arguments?.getString(keyBundleLink)
        ivClose.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = DialogAccept::class.java.name
        fun newInstance(balance: String, link: String): DialogFragment {
            val fragment = DialogAccept()
            val bundle = bundleOf("balance" to balance, "link" to link)
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}