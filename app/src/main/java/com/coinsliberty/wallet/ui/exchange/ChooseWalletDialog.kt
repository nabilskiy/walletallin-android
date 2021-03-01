package com.coinsliberty.wallet.ui.exchange

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseAdapter
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.LanguageContent
import com.coinsliberty.wallet.ui.dialogs.ChangeLanguageDialog
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_choose_wallet.*
import kotlinx.android.synthetic.main.dialog_send.*
import org.koin.android.viewmodel.ext.android.viewModel

class ChooseWalletDialog(
    private val isFromWallet: Boolean,
    private val viewModel: ExchangeViewModel
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.SendDialog

    lateinit var chooseWalletViewModel: ChooseWalletViewModel
    lateinit var adapter: AdapterChooseWallet

    var listener: ((WalletContent) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_choose_wallet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivClose2.setOnClickListener { dismiss() }

        if (!isFromWallet)
            tvTittle2.text = getString(R.string.convert_to)

        chooseWalletViewModel = ChooseWalletViewModel(viewModel, isFromWallet)
        searchEt.addTextChangedListener(searchListener)
        bindDataTo(chooseWalletViewModel.wallets, ::initAdapter)
        initAdapter(chooseWalletViewModel.wallets.value ?: mutableListOf())
    }


    fun initListeners(onChoosen: (WalletContent) -> Unit) {
        listener = onChoosen
    }

    private fun initAdapter(wallets: MutableList<WalletContent>) {
        Log.i(TAG, "initAdapter: ${wallets.size}")
        adapter = AdapterChooseWallet(
            wallets,
            object : AdapterChooseWallet.WalletClickListener {
                override fun onClick(wallet: WalletContent) {
                    if (isFromWallet) {
                        viewModel.setWalletFrom(wallet)
                    } else viewModel.setWalletTo(wallet)
                    dismiss()
                }
            }, chooseWalletViewModel.currentWallet!!
        )
        rv.adapter = adapter

    }

    private val searchListener =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i(TAG, "onTextChanged: ")
                chooseWalletViewModel.search(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

    companion object {
        val TAG = ChooseWalletViewModel::class.java.name

        fun newInstance(
            isFromWallet: Boolean,
            viewModel: ExchangeViewModel
        ): ChooseWalletDialog {
            val fragment = ChooseWalletDialog(isFromWallet, viewModel)
            fragment.setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme)
            return fragment
        }
    }

}