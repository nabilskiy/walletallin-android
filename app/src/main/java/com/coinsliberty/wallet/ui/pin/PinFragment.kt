package com.coinsliberty.wallet.ui.pin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.utils.extensions.invisible
import com.coinsliberty.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_pin.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class PinFragment : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_pin

    override val viewModel: PinViewModel by viewModel()
    override val navigator: PinNavigation = get()

    val bundle = Bundle()
    var pinCode: String? = null
    var isFaceId: Boolean? = false
    var isTouchId: Boolean? = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pinCode = arguments?.getString("code").toString()

        prepareData()


        etPin.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                changeColorPin(s.length)

                if (s.toString() == pinCode) {
                    //OK
                    toast("OK")
                }
            }
        })

//        ivTouchId.setOnClickListener {  }
//        ivFaceId.setOnClickListener {  }

    }

    private fun prepareData() {
        changeColorPin(0)

        isFaceId = arguments?.getBoolean("isFaceId")
        isTouchId = arguments?.getBoolean("isTouchId")

        if (isFaceId == null) {
            isFaceId = false
        }
        if (isTouchId == null) {
            isTouchId = false
        }

        if(!(isFaceId as Boolean)) {
            ivFaceId.invisible()
        }
        if(!(isTouchId as Boolean)) {
            ivTouchId.invisible()
        }
    }

    private fun changeColorPin(length: Int) {
        when (length) {
            0 -> {
                tvFirstValueActive.isActivated = true
                tvFirstValueActive.isEnabled = true
                tvSecondValue.isActivated = false
                tvSecondValue.isEnabled = true
                tvThirdValue.isActivated = false
                tvThirdValue.isEnabled = true
                tvFourthValue.isActivated = false
                tvFourthValue.isEnabled = true

                tvFirstValueActive.visible()

            }
            1 -> {
                tvFirstValue.isActivated = false
                tvFirstValue.isEnabled = false
                tvSecondValueActive.isActivated = true
                tvSecondValueActive.isEnabled = true
                tvThirdValue.isActivated = false
                tvThirdValue.isEnabled = true
                tvFourthValue.isActivated = false
                tvFourthValue.isEnabled = true

                tvFirstValueActive.invisible()
                tvSecondValueActive.visible()
            }
            2 -> {
                tvFirstValue.isActivated = false
                tvFirstValue.isEnabled = false
                tvSecondValue.isActivated = false
                tvSecondValue.isEnabled = false
                tvThirdValueActive.isActivated = true
                tvThirdValueActive.isEnabled = true
                tvFourthValue.isActivated = false
                tvFourthValue.isEnabled = true

                tvSecondValueActive.invisible()
                tvThirdValueActive.visible()
            }
            3 -> {
                tvFirstValue.isActivated = false
                tvFirstValue.isEnabled = false
                tvSecondValue.isActivated = false
                tvSecondValue.isEnabled = false
                tvThirdValue.isActivated = false
                tvThirdValue.isEnabled = false
                tvFourthValueActive.isActivated = true
                tvFourthValueActive.isEnabled = true

                tvThirdValueActive.invisible()
                tvFourthValueActive.visible()
            }
            4 -> {
                tvFirstValue.isActivated = false
                tvFirstValue.isEnabled = false
                tvSecondValue.isActivated = false
                tvSecondValue.isEnabled = false
                tvThirdValue.isActivated = false
                tvThirdValue.isEnabled = false
                tvFourthValue.isActivated = false
                tvFourthValue.isEnabled = false

                tvFourthValueActive.invisible()
            }
        }
    }


}