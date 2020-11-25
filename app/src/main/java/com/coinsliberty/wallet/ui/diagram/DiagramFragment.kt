package com.coinsliberty.wallet.ui.diagram

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.fragment_diagram.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class DiagramFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_diagram
    override val viewModel: StubViewModel by viewModel()
    override val navigator: StubNavigator = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        periodHandler()
        ivDiagramBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun periodHandler(){
        for (i in 0 until llDiagramPeriod.childCount){
            llDiagramPeriod.get(i).setOnClickListener {
                    for(j in 0 until llDiagramPeriod.childCount){
                        val period = llDiagramPeriod.get(j) as TextView
                        period.isActivated = false
                    }
                it.isActivated = true
            }
        }
    }

}
