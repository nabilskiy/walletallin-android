package com.example.coinsliberty.dialogs.sendDialog


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_send.*

private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"


class SendDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_send
    override val viewModel: SendBtcViewModel by viewModel()

    var listener: ((Boolean, String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    fun initListeners(onChoosen: (Boolean, String) -> Unit) {
        listener = onChoosen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTittle)
        val rates = arguments?.getDouble(keyBundleRates)
        val bundle = arguments?.getDouble(keyBundleBalance)
        val result = bundle!! * rates!!

        tvAmountCripto.setText(bundle.toString())
        tvAmountFiat.text = result.toString()

        tvAmountCripto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvAmountFiat.text = ((s.toString().toDoubleOrNull() ?: 0.0) * rates).toString()
            }
        })

        ivClose.setOnClickListener { dismiss() }

        btnSentCoin.setOnClickListener {
            viewModel.sendBtc("btc", tvAmountCripto.text.toString(), tvLink.text.toString())
            //listener?.invoke(tvAmountCripto.text.toString() != "" && tvAmountFiat.text.toString() != "")
        }
        subscribeLiveData()

    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::initResult)
    }

    private fun initResult(b: Boolean?) {
        listener?.invoke(b == true, tvAmountCripto.text.toString())
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