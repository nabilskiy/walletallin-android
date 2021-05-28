package com.tallin.wallet.dialogs.hint


import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinDialogFragment
import com.tallin.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_hint.*


class HintDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_hint
    override val viewModel: StubViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvHint.text = arguments?.getString("hint") ?: ""
    }

    companion object {
        val TAG: String = HintDialog::class.java.name
        fun newInstance(hint: String): HintDialog {
            val fragment = HintDialog()
            fragment.arguments = bundleOf("hint" to hint)
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}