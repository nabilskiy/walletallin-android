package com.tallin.wallet.ui.actions.buy

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseAdapter
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.BalanceInfoContent
import com.tallin.wallet.dialogs.ErrorDialog
import com.tallin.wallet.ui.actions.RateTimer
import com.tallin.wallet.ui.wallet.WALLET_VIEW_MODEL_TAG
import com.tallin.wallet.ui.wallet.adapters.MyWalletHolder
import com.tallin.wallet.ui.wallet.adapters.TransactionDataHolder
import com.tallin.wallet.ui.wallet.adapters.TransactionHolder
import com.tallin.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.tallin.wallet.ui.wallet.data.RateContent
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.hideKeyboard
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_buy_sell.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class BuySellFragment: BaseKotlinFragment() {

    override val layoutRes = R.layout.fragment_buy_sell
    override val viewModel: BuySellViewModel by viewModel()
    override val navigator: BuySellNavigation = get()

    private val rateTimer: RateTimer = get()

    private var walletData: List<WalletContent>? = null

    private var cryptoType = true
    private var currencyFiat = Currency.USD
    private var currencyCrypto = "BTC"

    private var balanceFiat = 0.0
    private var balanceCrypto = 0.0

    private var isBuy = true

    private var rate = 0.0

    private var normalNum = false

    private val adapter = BaseAdapter()
        .map(R.layout.item_wallet, MyWalletHolder { wc ->
            val balanceData: Double? =
                if (wc.type == "BTC")
                    viewModel.balanceData?.btc
                else viewModel.balanceData?.eth //todo ??
            Log.i(WALLET_VIEW_MODEL_TAG, "balance : $balanceData")

            val c = cryptoType
            val i = isBuy

            println("$c $i")

            if (cryptoType == isBuy){
                if (isBuy) {
                    currencyCrypto = wc.type ?: ""
                } else {
                    currencyFiat = Currency.valueOf(wc.type ?: "")
                }
                tvLeftCurrency.text = wc.type
                topTitle.text = wc.type
            } else {
                if (isBuy) {
                    currencyFiat = Currency.valueOf(wc.type ?: "")
                } else {
                    currencyCrypto = wc.type ?: ""
                }
                tvRightCurrency.text = wc.type
                bottomTitle.text = wc.type
            }
            rateTimer.isRun = false
            viewModel.refreshData(currencyFiat.getTitle(), currencyCrypto)
            ivBlockView.gone()
            clRecyclerView.gone()
        })
        .map(R.layout.item_transaction, TransactionHolder())
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = null
        adapter.removeAll()
        recyclerView.adapter = adapter
        viewModel.getCurrency()
        viewModel.walletList(currencyFiat.getTitle(), currencyCrypto)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateTimer.fragmentBuySell = this

        buySellDesign()
        goToCalc()

        ivBack.setOnClickListener { activity?.onBackPressed() }
        btnBuy.setOnClickListener {
            val oldBuy = isBuy
            isBuy = true
            buySellDesign()
            changeOperation(oldBuy != isBuy)
        }
        btnSell.setOnClickListener {
            val oldBuy = isBuy
            isBuy = false
            buySellDesign()
            changeOperation(oldBuy != isBuy)
        }
        ivBlockView.setOnClickListener {
            ivBlockView.gone()
            clRecyclerView.gone()
        }

        topInput.addTextChangedListener(countTextWatcher(isBuy, topInput, bottomInput))
        bottomInput.addTextChangedListener(countTextWatcher(!isBuy, bottomInput, topInput))

        leftLayout.setOnClickListener {
            changeCurrency(isBuy)
        }
        rightLayout.setOnClickListener {
            changeCurrency(!isBuy)
        }
        btnConfirm.setOnClickListener {
            navigator.goToOrderPreviewFragment(
                navController,
                if (isBuy) bottomInput.text.toString().toDouble() else topInput.text.toString().toDouble(),
                currencyFiat.getTitle(),
                if (isBuy) topInput.text.toString().toDouble() else bottomInput.text.toString().toDouble(),
                currencyCrypto,
                rate.toString(),
                isBuy
            )
        }

        subscribeLiveData()
    }

    private fun initTransactions() {
        adapter.itemsLoaded(//todo filtr
            ((walletData?.filter { it.crypto == cryptoType } ?: emptyList()))
        )
    }

    private fun initData(list: List<WalletContent>) {
        walletData = list
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun initCurrency(currency: Currency?) {
        if (currency == null) return
        this.currencyFiat = currency

        tvLeftCurrency.text = if (isBuy) currencyCrypto else currency.getTitle()
        topTitle.text = if (isBuy) currencyCrypto else currency.getTitle()

        tvRightCurrency.text = if (isBuy) currency.getTitle() else currencyCrypto
        bottomTitle.text = if (isBuy) currency.getTitle() else currencyCrypto
    }

    @SuppressLint("SetTextI18n")
    private fun initRate(rateData: RateContent){
        rate = rateData.rate  //todo
        tvRate.text = "${currencyCrypto}=${rateData.rate} ${currencyFiat.getTitle()}"

        goToCalc()
        rateTimer.startTimer()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.walletLiveData, ::initData)
        bindDataTo(viewModel.currency, ::initCurrency)
        bindDataTo(viewModel.rateLiveData, ::initRate)
        bindDataTo(viewModel.balanceDataLiveData, ::initBalance)
    }

    private fun initBalance(balance: BalanceInfoContent) {
        balanceCrypto = balance.btc ?: 0.0
        balanceFiat = balance.btc ?: 0.0
    }

    private fun countTextWatcher(isCrypto: Boolean, view: EditText, changeView: EditText) =
        object : TextWatcher {
            //private var change = true
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              /*  if (change){
                    editTextFormat(p0.toString())
                } else {
                    change = true
                }*/
            }
            override fun afterTextChanged(p0: Editable?) {
                calculator(isCrypto, view, changeView)
            }
        }
    /** timer */

    fun whenRun(rate: String){
        activity?.runOnUiThread {
            try {
                tvRate_available.text = rate
                tvRate_available.visible()
                tvWarning.gone()
                if(normalNum) {
                    btnConfirm.isEnabled = true
                    btnConfirm.alpha = 1f
                } else {
                    btnConfirm.isEnabled = false
                    btnConfirm.alpha = 0.3f
                }
                tvRate.setTextColor(getColorFromRes(R.color.light_gray_99))
            } catch (e: Exception) {
                println(e)
            }
        }
    }
    fun whenFinish(){
        activity?.runOnUiThread {
            try {
                btnConfirm.isEnabled = false
                btnConfirm.alpha = 0.3f
                tvRate_available.gone()
                tvRate.setTextColor(getColorFromRes(R.color.red_low_opacity))
                tvWarning.visible()
                viewModel.refreshData(currencyFiat.getTitle(), currencyCrypto)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun goToCalc(){
        if (!topInput.text.isNullOrBlank()){
            calculator(isBuy, topInput, bottomInput)
        }
    }

    /*private fun editTextFormat(string: String){
        if ()
    }*/

    private fun calculator(isCrypto: Boolean, view: EditText, changeView: EditText) {
        if (view.isFocused) {
            val str = view.text.toString()
            if (str.isNotBlank()) {
                try {
                    val strDouble = try {
                        str.toDouble()
                    } catch (e: Exception) {
                        0.0
                    }
                    when{
                        isCrypto -> {
                            val result = strDouble * rate
                         //   val afterFormat = strDouble.decimalFormat(2)
                            /** when {
                            strDouble > balanceCrypto -> {
                            getErrorDialog("You don't have enough cryptocurrency")
                            }
                            result > balanceFiat -> {
                            getErrorDialog("You don't have enough currency")
                            }
                            else -> {**/
                          /*  if (afterFormat != strDouble.toString()){
                                view.setText(afterFormat)
                            }*/
                            changeView.setText(result.decimalFormat(8))
                            /**   }
                            }**/
                        }
                        !isCrypto -> {
                            if (rate != 0.0) {
                                val result = strDouble / rate
                            //    val afterFormat = strDouble.decimalFormat(8)

                                /** when {
                                strDouble > balanceCrypto -> {
                                getErrorDialog("You don't have enough cryptocurrency")
                                }
                                result > balanceFiat -> {
                                getErrorDialog("You don't have enough currency")
                                }
                                else -> {**/

                              /*  if (afterFormat != strDouble.toString()){
                                    view.setText(afterFormat)
                                }*/
                                changeView.setText(result.decimalFormat(2))
                                /**       }
                                }**/
                            } else {
                                //todo !/0
                                rateTimer.isRun = false
                                viewModel.refreshData(currencyFiat.getTitle(), currencyCrypto)
                            }
                        }
                    }
                    if (strDouble > 0.00000000){
                        normalNum = true
                    }
                } catch (e: Exception) {
                    normalNum = false
                    println(e)
                }
            } else {
                normalNum = false
            }
        }
    }

    private fun getErrorDialog(message: String) {
        ErrorDialog.newInstance(message)
            .show(childFragmentManager, ErrorDialog.TAG)
    }

    private fun changeCurrency(type: Boolean){
        ivBlockView.visible()
        clRecyclerView.visible()
        cryptoType = type
        initTransactions()
        hideKeyboard(activity)
    }
    private fun changeOperation(isChange: Boolean = false){
        tvLeftCurrency.text = if (isBuy) currencyCrypto else currencyFiat.getTitle()
        tvRightCurrency.text = if (isBuy) currencyFiat.getTitle() else currencyCrypto
        topTitle.text = if (isBuy) currencyCrypto else currencyFiat.getTitle()
        bottomTitle.text = if (isBuy) currencyFiat.getTitle() else currencyCrypto
        if (isChange) {
            val str = topInput.text
            topInput.text = bottomInput.text
            bottomInput.text = str
        }
        goToCalc()
    }

    private fun buySellDesign(){
        if (isBuy) {
            btnBuy.setTextColor(getColorFromRes(R.color.txt_blue))
            btnSell.setTextColor(getColorFromRes(R.color.txt_gray))
            bottom_line_buy.setBackgroundColor(getColorFromRes(R.color.txt_blue))
            bottom_line_sell.setBackgroundColor(getColorFromRes(R.color.line_blue_with_transparency))
        } else {
            btnBuy.setTextColor(getColorFromRes(R.color.txt_gray))
            btnSell.setTextColor(getColorFromRes(R.color.txt_blue))
            bottom_line_buy.setBackgroundColor(getColorFromRes(R.color.line_blue_with_transparency))
            bottom_line_sell.setBackgroundColor(getColorFromRes(R.color.txt_blue))
        }
    }

    private fun Double.decimalFormat(charsAfterDot: Int) = String.format(Locale.ROOT, "%.${charsAfterDot}f", this)

}