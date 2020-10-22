package com.example.coinsliberty.ui.exchange

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import kotlinx.android.synthetic.main.fragment_exchange.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class ExchangeFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_exchange
    override val viewModel: ExchangeViewModel by viewModel()
    override val navigator: ExchangeNavigator = get()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val spinnerPay: Spinner = spinnerPay

//
//        this.context?.let {
//            ArrayAdapter.createFromResource(
//                it,
//                R.array.item_exchange,
//                R.layout.spinner_exchange
//            ).also { adapter ->
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                // Apply the adapter to the spinner
//                spinnerPay.adapter = adapter
//            }
//        }
//
    }




}
