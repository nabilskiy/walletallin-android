package com.tallin.wallet.ui.fragmentActions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_actions.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ActionsFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_actions
    override val viewModel: ActionsViewModel by viewModel()
    override val navigator: ActionsNavigation = get()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.getUser()
        if (user?.wallet?.kycProgramStatus == 1){
            btn3.visible()
            btn3.text = resources.getString(R.string.buy_sell)
            btn1.text = resources.getString(R.string.send)
            btn2.text = resources.getString(R.string.receive)
            btn4.text = resources.getString(R.string.swap)
            btn3.setOnClickListener { clickBuySell() }
            btn1.setOnClickListener { clickSend() }
            btn2.setOnClickListener { clickReceive() }
            btn4.setOnClickListener { clickSwap() }
        } else {
            btn3.gone()
            btn1.text = resources.getString(R.string.send)
            btn2.text = resources.getString(R.string.receive)
            btn4.text = resources.getString(R.string.swap)
            btn4.background = resources.getDrawable(R.drawable.bg_item_wallet_border, null)
            btn1.setOnClickListener { clickSend() }
            btn2.setOnClickListener { clickReceive() }
            btn4.setOnClickListener { clickSwap() }
        }

        tvHello.text = resources.getString(R.string.hello_s, user?.firstName ?: "")

        if (!viewModel.getKycStatus()){
            navigator.goToKYCProcess(navController)
        }
    }

    private fun clickBuySell(){
        navigator.goToBuyFragment(navController)
    }
    private fun clickSend(){

    }
    private fun clickReceive(){

    }
    private fun clickSwap(){
        navigator.goToExchange(navController)
    }
}