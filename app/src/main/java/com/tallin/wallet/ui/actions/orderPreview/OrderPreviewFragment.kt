package com.tallin.wallet.ui.actions.orderPreview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.ui.actions.RateTimer
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_order_preview.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class OrderPreviewFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_order_preview
    override val viewModel: OrderPreviewViewModel by viewModel()
    override val navigator: OrderPreviewNavigation = get()

    private val rateTimer: RateTimer = get()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateTimer.fragmentOrderPreview = this

        val currencyFiat = arguments?.getDouble("[Buy-Sell]fiatAmount")
        val currencyFiatName = arguments?.getString("[Buy-Sell]fiatCurrency")
        val currencyCrypto = arguments?.getDouble("[Buy-Sell]cryptoAmount")
        val currencyCryptoName = arguments?.getString("[Buy-Sell]cryptoCurrency")
        val rate = arguments?.getString("[Buy-Sell]rate")
        val operation = arguments?.getBoolean("[Buy-Sell]operation")

        tvFiatCount_o.text = "$currencyFiat$currencyFiatName"
        tvCryptoCount_o.text = "$currencyCrypto"
        tvCryptoName_o.text = currencyCryptoName
        tvRateCount_o.text = "$rate $currencyFiatName"


        ivBack_o.setOnClickListener { activity?.onBackPressed() }
        btnConfirm_o.setOnClickListener {
            when (operation){
                true -> viewModel.doBuy(currencyCryptoName ?: "", currencyCrypto ?: 0.0)
                false -> viewModel.doSell(currencyFiatName ?: "", currencyFiat ?: 0.0)
                null -> activity?.onBackPressed()
            }
        }

        subscribeLiveData()
    }

    fun whenRun(rate: String, rateInt: Int){
        activity?.runOnUiThread {
            try {
            tvRate_available_o?.visible()
            tvRate_changed_o?.gone()
            btnConfirm_o.isEnabled = true
            btnConfirm_o.alpha = 1f
            tvRate_available_o.text = rate
            progressBar_o.progress = rateInt
            } catch (e: Exception) {
                println(e)
            }
        }
    }
    fun whenFinish(){
        activity?.runOnUiThread {
            try {
                btnConfirm_o.isEnabled = false
                btnConfirm_o.alpha = 0.3f
                tvRate_changed_o.visible()
                tvRate_available_o.gone()
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultLiveData, ::result)
    }

    private fun result(b: Boolean){
        if (b){
            navigator.goToConfirmationFragment(navController, b)
        }
    }
}