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
private const val keyBundleBalance = "balance"
private const val keyBundleLink = "link"
class AcceptDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_accept
    override val viewModel: StubViewModel by viewModel()



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
        val TAG: String = AcceptDialog::class.java.name
        fun newInstance(balance: String, link: String): DialogFragment {
            val fragment = AcceptDialog()
            val bundle = bundleOf(keyBundleBalance to balance, keyBundleLink to link)
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGreenBG)
            return fragment
        }
    }
}