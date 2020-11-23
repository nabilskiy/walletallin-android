package com.coinsliberty.wallet.ui.pin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.dialogs.faceIdDialog.FaceIdDialog
import com.coinsliberty.wallet.dialogs.touchIdDialog.TouchIdDialog
import com.coinsliberty.wallet.utils.extensions.invisible
import com.coinsliberty.wallet.utils.extensions.visible
import com.coinsliberty.wallet.utils.manager.TypeBiometrics
import kotlinx.android.synthetic.main.fragment_pin.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class PinFragment : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_pin

    override val viewModel: PinViewModel by viewModel()
    override val navigator: PinNavigation = get()

    private var addData: String? = null

    var pinCode: String? = null
    var isFaceId: Boolean? = false
    var isTouchId: Boolean? = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pinCode = viewModel.getPin()

        if(pinCode.isNullOrEmpty().not()) {
            when (viewModel.getTypeOfBiometric()) {
                TypeBiometrics.TOUCH_ID -> {
                    ivTouchId.visible()
                    TouchIdDialog().apply {
                        initListeners {
                            if(it) {
                                navigator.goToMain(navController)
                            } else {
                                showKeyboard()
                            }
                        }
                    }.show(childFragmentManager, TouchIdDialog.TAG)
                }
                TypeBiometrics.FACE_ID -> {
                    ivFaceId.visible()
                    FaceIdDialog().apply {
                        initListeners {
                            if(it) {
                                navigator.goToMain(navController)
                            } else {
                                showKeyboard()
                            }
                        }
                    }.show(childFragmentManager, FaceIdDialog.TAG)
                }
                else -> {
                }
            }
        }

        prepareData()


        etPin.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
                Unit

            override fun afterTextChanged(s: Editable) {
                changeColorPin(s.length)
            }
        })

        ivTouchId.setOnClickListener {
            TouchIdDialog().apply {
                initListeners {
                    if(it) {
                        navigator.goToMain(navController)
                    } else {
                        showKeyboard()
                    }
                }
            }.show(childFragmentManager, TouchIdDialog.TAG)
        }
        ivFaceId.setOnClickListener {
            FaceIdDialog().apply {
                initListeners {
                    if(it) {
                        navigator.goToMain(navController)
                    } else {
                        showKeyboard()
                    }
                }
            }.show(childFragmentManager, FaceIdDialog.TAG)
        }

    }


    override fun onStart() {
        super.onStart()

        showKeyboard()
    }

    private fun showKeyboard() {
        etPin.requestFocus()
        val imgr: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.showSoftInput(etPin, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun prepareData() {
        changeColorPin(0)
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

                if (pinCode.isNullOrEmpty()) {
                    if (addData.isNullOrEmpty()) {
                        addData = etPin.text.toString()
                        etPin.setText("")
                        changeColorPin(0)
                    } else {
                        viewModel.savePin(addData ?: "")
                        navigator.goToMain(navController)
                    }
                } else {
                    if (etPin.text.toString() == pinCode) {
                        navigator.goToMain(navController)
                    } else {
                        toast("Fail")
                        etPin.setText("")
                        changeColorPin(0)
                    }
                }
            }
        }
    }

}