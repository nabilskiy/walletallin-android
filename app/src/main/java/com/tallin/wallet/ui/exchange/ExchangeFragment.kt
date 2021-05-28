package com.tallin.wallet.ui.exchange

import android.os.Bundle
import android.util.Log
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.BalanceInfoContent
import com.tallin.wallet.data.response.SwapInfoData
import com.tallin.wallet.data.response.SwapLimitsData
import com.tallin.wallet.dialogs.ConfirmExchangeDialog
import com.tallin.wallet.ui.custom.CustomAmountEditText
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.ui.widgets.inputField.switchviews.SwitchExchange
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_exchange.*
import kotlinx.android.synthetic.main.fragment_my_wallet.tvTotalBalanceCrypto
import kotlinx.android.synthetic.main.fragment_my_wallet.tvTotalBalanceFiat
import kotlinx.android.synthetic.main.fragment_my_wallet.tvTotalBalanceFiatName
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class ExchangeFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_exchange
    override val viewModel: ExchangeViewModel by viewModel()
    override val navigator: ExchangeNavigator = get()

    private val TAG = ExchangeFragment::class.java.name

    private var currency: Currency = Currency.USD

    private var walletData: List<WalletContent>? = null
    private lateinit var amountET: CustomAmountEditText
    private var confirmationDialog: ConfirmExchangeDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: ")
        subscribeLiveData()
        initEditText()
        initListeners()
        viewModel.getCurrency()
        viewModel.initData()
    }

    private fun initEditText() {
        amountET = CustomAmountEditText(etAmountFrom).also {
            it.underline = true
        }
    }

    private fun initListeners() {
        amountET.initListeners()
        amountET.onAmountChangedListener = fromAmountChangeListener
        switchExchange.onSwitchElementClickListener = onSwitchStateChangeListener
        btnSwap.setOnClickListener { viewModel.swapCryptos() }
        btnExhange.setOnClickListener { makeExchange() }
        llTo.setOnClickListener {
            ChooseWalletDialog.newInstance(false, viewModel)
                .show(parentFragmentManager, ChooseWalletDialog.TAG)
        }
        llFrom.setOnClickListener {
            ChooseWalletDialog.newInstance(true, viewModel)
                .show(parentFragmentManager, ChooseWalletDialog.TAG)
        }
    }


    private fun subscribeLiveData() {
        bindDataTo(viewModel.availableBalanceDataLiveData, ::onAvailableBalanceLiveDataChanged)
        bindDataTo(viewModel.result, ::onResultLiveDataChanged)
        bindDataTo(viewModel.walletLiveData, ::onWalletLiveDataChanged)
        bindDataTo(viewModel.balanceDataLiveData, ::onBalanceLiveDataChanged)
        bindDataTo(viewModel.currency, ::onCurrencyLiveDataChanged)
        bindDataTo(viewModel.walletFrom, ::onWalletFromLiveDataChanged)
        bindDataTo(viewModel.walletTo, ::onWalletToLiveDataChanged)
        bindDataTo(viewModel.swapLimitsData, ::onSwapLimitsLiveDataChanged)
        bindDataTo(viewModel.swapInfoData, ::onSwapInfoLiveDataChanged)
    }

    private fun onResultLiveDataChanged(b: Boolean) {
        if (b)
            viewModel.initData()


    }


    private fun onWalletToLiveDataChanged(wallet: WalletContent) {
        toCurrencyShortName.text = wallet.title
        toCurrencyFullName.text = wallet.type
        toIv.setImageResource(wallet.ico)
        tvAmountTo.setTextColor(wallet.color)
        toCurrencyShortName.setTextColor(wallet.color)
        receivingAmountCrypto.setTextColor(wallet.color)
    }

    private fun onWalletFromLiveDataChanged(wallet: WalletContent) {
        fromCurrencyShortName.text = wallet.title
        fromCurrencyFullName.text = wallet.type
        fromIv.setImageResource(wallet.ico)
        amountET.walletColor = wallet.color

        val availableBalances = viewModel.availableBalanceDataLiveData.value
            ?: // TODO: 24.02.2021 alert?
            return
        val balanceCrypto =
            availableBalances.balanceFrom
        val iHaveCryptoString = "${getString(R.string.i_have)} " +
                "${
                    String.format(
                        "%.8f",
                        balanceCrypto
                    )
                } ${wallet.title}"
        val balanceFiat =
            (balanceCrypto ?: 0.0) * (viewModel.fromRates ?: 0.0)
        val balanceFiatString = String.format(
            "%.2f",
            balanceFiat
        )
        val iHaveFiatString =
            "${currency.getSymbol()} $balanceFiatString"

        iHaveTvFrom.text = iHaveCryptoString
        iHaveInCurrencyTvFrom.text = iHaveFiatString

        fromCurrencyShortName.setTextColor(wallet.color)
        exchangingAmountCrypto.setTextColor(wallet.color)
    }


    private fun onSwapLimitsLiveDataChanged(swapLimitsData: SwapLimitsData) {
        Log.i(TAG, "onSwapLimitsLiveDataChanged: ")
        amountET.limits = swapLimitsData
        amountET.updateAmount(swapLimitsData.minFrom, false)
        switchExchange.setMin()
    }

    private fun onSwapInfoLiveDataChanged(info: SwapInfoData) {
        if (confirmationDialog == null)
            confirmationDialog =
                ConfirmExchangeDialog.newInstance(info, confirmationExchangeClickListener)
        confirmationDialog!!.show(parentFragmentManager, ConfirmExchangeDialog.TAG)
    }


    private fun onWalletLiveDataChanged(list: List<WalletContent>) {
        walletData = list
    }

    private fun onBalanceLiveDataChanged(balance: BalanceInfoContent) {
        tvTotalBalanceCrypto.text = String.format("%.8f", balance.btc ?: 0.0)
        tvTotalBalanceFiat.text = String.format(
            "%.2f",
            ((balance.btc ?: 0.0) * (viewModel.fromRates ?: 0.0))
        )
    }

    private fun onAvailableBalanceLiveDataChanged(balance: AvailableBalanceLiveData.AvailableBalanceLiveDataData) {
        availableBalanceAmount.text = String.format("%.8f", balance.balanceFrom ?: 0.0)
        availableBalanceCurrencyAmount.text = String.format(
            "%.2f",
            ((balance.balanceFrom ?: 0.0) * (viewModel.fromRates ?: 0.0))
        )
        amountET.balance = balance.balanceFrom
    }

    private fun onCurrencyLiveDataChanged(currency: Currency?) {
        if (currency == null) return
        this.currency = currency

        availableBalanceCurrencyCurrency.text = currency.getTitle()
        tvTotalBalanceFiatName.text = currency.getTitle()
    }

    private val onSwitchStateChangeListener =
        object : SwitchExchange.OnSwitchElementClickListener {
            override fun onElementClicked(switchElement: SwitchExchange.SwitchElement) {
                when (switchElement) {
                    SwitchExchange.SwitchElement.MIN -> amountET.updateAmount(
                        viewModel.swapLimitsData.value?.minFrom ?: 0.0,
                        false
                    )
                    SwitchExchange.SwitchElement.HALF -> amountET.updateAmount(
                        (viewModel.availableBalanceDataLiveData.value?.balanceFrom ?: 0.0) / 2,
                        false
                    )
                    SwitchExchange.SwitchElement.ALL -> amountET.updateAmount(
                        viewModel.availableBalanceDataLiveData.value?.balanceFrom
                            ?: 0.0 / (viewModel.fromRates ?: 1.0),
                        false
                    )
                }
            }
        }

    private fun makeExchange() {
        if (amountET.validAmount())
            viewModel.getSwapInfo(amountET.amount)
    }

    private val fromAmountChangeListener =
        object : CustomAmountEditText.OnEditTextAmountChangeListener {
            override fun onChanged(amount: Double, needToUpdateSwitch: Boolean) {
                if (needToUpdateSwitch) {
                    Log.i(TAG, "onChanged: SET NOTHING")
                    switchExchange.setNothing()
                }
                updateAmount(amount)
            }
        }

    private val confirmationExchangeClickListener =
        object : ConfirmExchangeDialog.ConfirmationExchangeClickListener {
            override fun onOkClick() {
                confirmationDialog = null
                viewModel.makeExchange()
            }

            override fun onDismiss() {
                confirmationDialog = null
            }
        }


    private fun updateAmount(amount: Double) {
        var tmpString: String? = null
        val amountStr = String.format(
            "%.8f",
            amount
        )
        val amountTo = amount * (viewModel.swapLimitsData.value?.rate ?: 0.0)


        tvAmountTo.text =
            String.format(
                "%.8f",
                amountTo
            )

        tmpString = String.format(
            "%.2f",
            amountTo * (viewModel.toRates ?: 0.0)
        )
        tmpString = "${currency.getSymbol()} $tmpString"
        iWantAmountFiat.text = tmpString

        tmpString = "$amountStr ${viewModel.walletFrom.value?.title}"
        Log.i(TAG, "updateAmount: $tmpString")
        exchangingAmountCrypto.text = tmpString

        tmpString = String.format(
            "%.2f",
            amount * (viewModel.fromRates ?: 0.0)
        )
        tmpString = "${currency.getSymbol()}$tmpString"
        exchangingAmountFiat.text = tmpString

        tmpString = String.format(
            "%.8f",
            amountTo
        )
        tmpString = "+$tmpString ${viewModel.walletTo.value?.title}"
        receivingAmountCrypto.text = tmpString

        tmpString = String.format(
            "%.2f",
            amountTo * (viewModel.toRates ?: 0.0)
        )
        tmpString = "${currency.getSymbol()}$tmpString"
        receivingAmountFiat.text = tmpString
    }


}
