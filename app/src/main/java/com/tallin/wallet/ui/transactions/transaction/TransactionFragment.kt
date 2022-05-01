package com.tallin.wallet.ui.transactions.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.tallin.wallet.BottomFragment
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseAdapter
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.BalanceInfoResponse
import com.tallin.wallet.data.response.TransactionItem
import com.tallin.wallet.dialogs.AcceptDialog
import com.tallin.wallet.dialogs.makeTransaction.ReceiveDialog
import com.tallin.wallet.dialogs.makeTransaction.SendDialog
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.wallet.adapters.TransactionDataHolder
import com.tallin.wallet.ui.wallet.adapters.TransactionHolder
import com.tallin.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import com.tallin.wallet.utils.isDifferrentDate
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.android.synthetic.main.fragment_transaction.tvTotalBalance
import kotlinx.android.synthetic.main.fragment_transaction.tvTotalBalanceCrypto
import kotlinx.android.synthetic.main.fragment_transaction.tvTotalBalanceFiatCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


private const val keyBundleBalance = "balance"
private const val keyBundleWallet = "wallet"
private const val keyBundleRates = "rates"
private const val keyBundleWalletType = "walletType"
private const val keyWalletColor = "walletColor"
private const val keyWalletCoefficient = "walletCoefficient"

class TransactionFragment : BaseKotlinFragment() {

    override val layoutRes = R.layout.fragment_transaction
    override val viewModel: TransactionViewModel by viewModel()
    override val navigator: TransactionNavigation = TransactionNavigation()

    private var makeTransactionDialog: SendDialog? = null
    private var receiveDialog: ReceiveDialog? = null

    private var rates: Double = 0.0
    private var currency: Currency = Currency.USD
    private var balanceData: Double = 0.0
    private var walletIco: Int? = null
    private var walletColor: Int? = null
    private var walletCoefficient: Double = 1.0
    private lateinit var walletType: String

    val adapter = BaseAdapter()
        .map(R.layout.item_transaction, TransactionHolder(){
            ((parentFragment as NavHostFragment).parentFragment as? BottomFragment)?.goToTransactionDocument(it)
        })
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnReceive.setOnClickListener { showReceiveDialog() }
        btnSend.setOnClickListener { showTransactionDialog() }


        rates = arguments?.getDouble(keyBundleRates) ?: 0.0
        balanceData = arguments?.getDouble(keyBundleBalance) ?: 0.0
        walletIco = arguments?.getInt(keyBundleWallet)
        walletColor = arguments?.getInt(keyWalletColor)
        walletType = arguments?.getString(keyBundleWalletType) ?: ""
        walletCoefficient = arguments?.getDouble(keyWalletCoefficient) ?: 1.0
        viewModel.walletType = walletType

        ivTransactionLeftIcon.setOnClickListener {
            activity?.onBackPressed()
        }

//        clBTCPriceForOne.setOnClickListener {
//            navigator.goToDiagram(navController)
//        }

        subscribeLiveData()

        rvTransactions.adapter = adapter
    }

    private fun showReceiveDialog() {
        if (receiveDialog == null) {
            receiveDialog =
                ReceiveDialog.newInstance(
                    walletType
                )
        }
        receiveDialog?.show(childFragmentManager, ReceiveDialog.TAG)
    }

    private fun showTransactionDialog() {
        if (makeTransactionDialog == null) {
            makeTransactionDialog =
                SendDialog.getInstance(
                    balanceData,
                    walletType,
                    rates,
                    walletColor ?: 0,
                    walletIco ?: 0,
                    walletCoefficient
                )
        }
        makeTransactionDialog?.show(childFragmentManager, SendDialog.TAG)
//        if (makeTransactionDialog == null) {
//            makeTransactionDialog =
//                MakeTransactionDialog.newInstance(
//                    walletType,
//                    rates,
//                    balanceData ?: 0.0,
//                    null,
//                    "17325782351905019asdofjkas",
//                    "ss",
//                    walletIco!!,
//                    isSend
//                )
//        }
//        makeTransactionDialog?.apply {
//            initListeners { result, text ->
//                showResult(result, text)
//            }
//        }
//            ?.show(childFragmentManager, MakeTransactionDialog.TAG)
    }

    override fun onStart() {
        super.onStart()
        activity.let {
            (it as MainActivity).showPin = false
        }
        viewModel.getCurrency()
        viewModel.getTransaction()
        changeNavigationBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.balance_header_color
            )
        )
    }

    override fun onPause() {
        super.onPause()
        val currentFragmentMenu = navController?.currentDestination?.label
        if (currentFragmentMenu != "TransactionFragment") {
            activity.let {
                (it as MainActivity).showPin = true
            }
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.balanceLiveData, ::initBalance)
        bindDataTo(viewModel.currency, ::initCurrency)
    }

    private fun initCurrency(currency: Currency?) {
        if (currency == null) return
        this.currency = currency
    }

    private fun initTransactions(list: List<TransactionItem>?) {
        if (list.isNullOrEmpty()) {
            ivNoTransaction.visible()
            tvNoTransaction.visible()
            return
        }
        ivNoTransaction.gone()
        tvNoTransaction.gone()

        adapter.itemsAdded(listOf("Transactions"))
        adapter.itemsAdded(getTransactions(list))
    }

    @SuppressLint("SetTextI18n")
    private fun initBalance(balance: BalanceInfoResponse) {
        val priceForOne = "1$walletType=${String.format("%.2f", rates)}"
        tvPriceOfOne.text = priceForOne
        val totalBalance = "${getString(R.string.total_fiat)} ${currency.getTitle()}"
        tvTotalBalance.text = totalBalance
        tvTotalBalanceCrypto.text = String.format("%.8f", balanceData)
        tvTotalBalanceNameCrypto.text = walletType
        tvTotalBalanceFiatCurrency.text =
            "${getString(R.string.total_fiat)} ${currency.getTitle().toUpperCase(Locale.ROOT)}:"
        tvTotalBalanceFiat.text =
            String.format(
                "%.2f",
                balanceData.times(rates)
            )
        tvTotalBalanceFiatName.text = currency.getTitle()

        if (walletIco != null)
            ivTransactionLogo.setImageResource(walletIco!!)
        viewModel.updateBalance()
    }

    private fun getTransactions(list: List<TransactionItem>?): List<Any>? {
        if (list.isNullOrEmpty()) return emptyList()

        val resultList = ArrayList<Any>()
        var lastItem: TransactionItem? = null
        list.forEachIndexed { _, it ->
            if (lastItem == null || isDifferrentDate(lastItem?.time ?: 0, it.time ?: 0)) {
                resultList.add(it.time ?: 0)
                lastItem?.typeItem = 2
                it.typeItem = 0
            }

            resultList.add(it.apply {
                it.amountUsd = String.format(
                    "%.2f",
                    (it.amount?.toDouble() ?: 0.0) * (rates)
                )

            })
            it.currency = currency
            lastItem = it
        }
        (resultList[resultList.size - 1] as? TransactionItem)?.typeItem = 2

        return resultList
    }

    private fun showResult(it: Boolean, balance: String? = null) {
        if (it) {
            makeTransactionDialog?.dismiss()
            AcceptDialog.newInstance(balance ?: "", "Success")
                .show(childFragmentManager, AcceptDialog.TAG)
        } else {
            //ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }

    companion object {
        val TAG: String =
            com.tallin.wallet.ui.transactions.transaction.TransactionFragment::class.java.name

        fun getBundle(
            rates: Double?,
            balance: Double?,
            wallet: Int?,
            walletType: String?,
            walletColor: Int,
            coefficient: Double
        ): Bundle {
            val bundle = bundleOf(
                keyBundleRates to rates,
                keyBundleBalance to balance,
                keyBundleWallet to wallet,
                keyBundleWalletType to walletType,
                keyWalletColor to walletColor,
                keyWalletCoefficient to coefficient
            )
            return bundle
        }
    }
}
