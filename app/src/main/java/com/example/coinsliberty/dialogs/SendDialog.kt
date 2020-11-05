package com.example.coinsliberty.dialogs


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_send.*

private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"


class SendDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_send
    override val viewModel: SendBtcViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTittle)
        val rates = arguments?.getDouble(keyBundleRates)
        val bundle = arguments?.getDouble(keyBundleBalance)
        val result = bundle!! * rates!!

        tvAmountCripto.text = bundle.toString()
        tvAmountFiat.text = result.toString()


        Log.e("!", "rates")
        Log.e("!!", rates.toString() ?: "empty")
        Log.e("!", "balance")
        Log.e("!!", bundle.toString() ?: "empty")

        ivClose.setOnClickListener { dismiss() }

        btnSentCoin.setOnClickListener {
            listener?.invoke(tvAmountCripto.text.toString() != "" && tvAmountFiat.text.toString() != "")
            viewModel.sendBtc("btc", "${result}", "nfkjwnfkjnfknerkjfj")
        }

    }

    companion object {
        val TAG: String = SendDialog::class.java.name
        fun newInstance(
            tittle: String, rates: Double, balance: Double
        ): SendDialog {
            val fragment = SendDialog()
            val bundle = bundleOf(
                keyBundleTittle to tittle,
                keyBundleRates to rates,
                keyBundleBalance to balance
            )
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGreenBG)
            return fragment
        }
    }
}