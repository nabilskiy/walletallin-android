package com.tallin.wallet.ui.actions.orderPreview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.BuySellResponse
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

    private var operationId: Int? = null


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateTimer.fragmentOrderPreview = this

        val currencyFiat = arguments?.getString("[Buy-Sell]fiatAmount")
        val currencyFiatName = arguments?.getString("[Buy-Sell]fiatCurrency")
        val currencyCrypto = arguments?.getString("[Buy-Sell]cryptoAmount")
        val currencyCryptoName = arguments?.getString("[Buy-Sell]cryptoCurrency")
        val rate = arguments?.getString("[Buy-Sell]rate")
        val operation = arguments?.getBoolean("[Buy-Sell]operation")

        tvFiatCount_o.text = "$currencyFiat$currencyFiatName"
        tvCryptoCount_o.text = "$currencyCrypto"
        tvCryptoName_o.text = currencyCryptoName
        tvRateCount_o.text = "$rate $currencyFiatName"

        btnConfirm_o.text = when (operation){
            true -> "Buy Now"
            false -> "Sell Now"
            else -> "Exit"
        }

        ivBack_o.setOnClickListener { activity?.onBackPressed() }
        btnConfirm_o.setOnClickListener {

            val currentFragment =
                Navigation.findNavController(requireActivity(), R.id.frameLayout).currentDestination?.label
            println(currentFragment)
            println(currentFragment)

            when (operation){
                true ->{
                    viewModel.doBuy(
                        currencyFiatName ?: "",
                        currencyCryptoName ?: "",
                        currencyFiat ?: "0.0",
                        currencyCrypto ?: "0.0"
                    )
                }
                false -> {
                    viewModel.doSell(
                        currencyFiatName ?: "",
                        currencyCryptoName ?: "",
                        currencyCrypto ?: "0.0",
                        currencyFiat ?: "0.0"
                    )
                }
                null -> activity?.onBackPressed()
            }
        }
        view_background.setOnClickListener {}
        nextBuySell.setOnClickListener {
            navigator.goToConfirmationFragment(navController, true)
            if (operationId != null) {
                when (operation) {
                    true -> viewModel.confirmBuy(operationId!!, input2faCode.text.toString())
                    false -> viewModel.confirmSell(operationId!!, input2faCode.text.toString())
                    else -> activity?.onBackPressed()
                }
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

    private fun result(result: BuySellResponse){
        if (result.result == true){
            when (result.status){
                "success" -> {
                    if (result.operationId != null) {
                        ll2fa.visible()
                        view_background.visible()
                        operationId = result.operationId
                    } else navigator.goToConfirmationFragment(navController, result.result)
                }
                "created" -> {
                    navigator.goToConfirmationFragment(navController, result.result)
                }
            }
        } else {
            navigator.goToConfirmationFragment(navController, result.result ?: false)
        }
    }
}