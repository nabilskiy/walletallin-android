package com.coinsliberty.wallet.dialogs

import android.graphics.Typeface.BOLD
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.data.response.SwapInfoData
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_exchange_confirmation.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class ConfirmExchangeDialog(
    private val info: SwapInfoData,
    private val listener: ConfirmationExchangeClickListener
) :
    BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_exchange_confirmation
    override val viewModel: StubViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCancel.setOnClickListener {
            dismiss()
            listener.onDismiss()
        }
        btnConfirm.setOnClickListener {
            dismiss()
            listener.onOkClick()
        }
        setInfo(info)
    }

    override fun onResume() {
        super.onResume()
        setWindow()
    }

    fun setInfo(info: SwapInfoData) {
        confirmationDescr.text = getInfoString(info)
    }

    private fun getInfoString(info: SwapInfoData): SpannableStringBuilder {
        val amountFrom = String.format(
            "%.8f",
            info.amountFrom
        )
        val amountTo = String.format(
            "%.8f",
            info.amountTo
        )
        val t1 = "${getString(R.string.exchange_confirmation_descr)} "
        val t2 = "${info.from.toUpperCase(Locale.ROOT)}  $amountFrom"
        val t3 = " ${getString(R.string.exchange_confirmation_to)} "
        val t4 = "${info.to}  $amountTo"
        val t5 = "?"
        return SpannableStringBuilder(t1 + t2 + t3 + t4 + t5).apply {
            setSpan(
                StyleSpan(BOLD),
                t1.length,
                t1.length + t2.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                StyleSpan(BOLD),
                t1.length + t2.length + t3.length,
                t1.length + t2.length + t3.length + t4.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
//        return "${getString(R.string.exchange_confirmation_descr)}\n${info.from}" +
//                " $amountFrom ${getString(R.string.exchange_confirmation_to)} ${info.to} $amountTo"
    }

    private fun setWindow() {
        if (dialog?.window == null)
            return
        var width: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = dialog?.window!!.windowManager.currentWindowMetrics
            val insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(
                    WindowInsets.Type.systemBars()
                )
            width = windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            dialog?.window!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            width = displayMetrics.widthPixels
        }

        width = width * 90 / 100

        val params = dialog?.window!!.attributes
        params.width = width

        dialog?.window!!.attributes = params
        dialog?.window!!.setGravity(Gravity.CENTER)
        dialog?.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    interface ConfirmationExchangeClickListener {
        fun onOkClick()
        fun onDismiss()
    }

    companion object {
        val TAG: String = ConfirmExchangeDialog::class.java.name
        fun newInstance(
            info: SwapInfoData,
            listener: ConfirmationExchangeClickListener
        ): ConfirmExchangeDialog {
            val fragment = ConfirmExchangeDialog(info, listener)
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}