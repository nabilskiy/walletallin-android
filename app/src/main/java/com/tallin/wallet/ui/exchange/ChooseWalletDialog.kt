package com.tallin.wallet.ui.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.tallin.wallet.R
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.utils.extensions.bindDataTo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_choose_wallet.*

class ChooseWalletDialog(
    private val isFromWallet: Boolean,
    private val viewModel: ExchangeViewModel
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.SendDialogGrey

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

    override fun onStart() {
        super.onStart()
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.balance_header_color)
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
            wallets.filter { it.crypto } as MutableList<WalletContent>,
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