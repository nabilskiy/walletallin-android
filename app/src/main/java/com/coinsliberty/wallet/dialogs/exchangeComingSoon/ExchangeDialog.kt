package com.coinsliberty.wallet.dialogs.exchangeComingSoon

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.coinsliberty.wallet.BottomFragmant
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_exchange_coming_soon.*
import org.koin.android.viewmodel.ext.android.viewModel


class ExchangeDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_exchange_coming_soon
    override val viewModel: StubViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = dialog!!.window

        window!!.setGravity(Gravity.BOTTOM)
        val params = window.attributes
        params.y = 100
        window.attributes = params

        isCancelable = false
        ivDialogEchangeClose.setOnClickListener {
            activity?.onBackPressed()
            listener?.invoke(true)
            dismiss()
        }
        buttonExchangeClose.setOnClickListener {
            activity?.onBackPressed()
            listener?.invoke(true)
            dismiss()
        }
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = ExchangeDialog::class.java.name
        fun newInstance(): ExchangeDialog {
            val fragment = ExchangeDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}