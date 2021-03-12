package com.coinsliberty.wallet.ui.widgets.inputField.switchviews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import com.coinsliberty.wallet.R
import kotlinx.android.synthetic.main.switch_echange.view.*


class SwitchExchange @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val TAG = "CUSTOM_EXCHANGE_SWITCH_TAG"


    var onSwitchElementClickListener: OnSwitchElementClickListener? = null

    private var selectedElement: SwitchElement? = SwitchElement.MIN


    init {
        inflate(context, R.layout.switch_echange, this)

        initListeners()
    }

    private fun initListeners() {
        tvMin.setOnClickListener { onElementClick(SwitchElement.MIN) }
        tvHalf.setOnClickListener { onElementClick(SwitchElement.HALF) }
        tvAll.setOnClickListener { onElementClick(SwitchElement.ALL) }
    }


    private fun onElementClick(element: SwitchElement) {
        if (element == selectedElement)
            return
        setNothing()
        selectedElement = element
        setTvBackground(element.getTv(this), true)
        hideVerticalViews(element)
        onSwitchElementClickListener?.onElementClicked(element)
    }

    private fun hideVerticalViews(element: SwitchElement) {
        when (element) {
            SwitchElement.MIN -> view0.visibility = INVISIBLE
            SwitchElement.HALF -> {
                view0.visibility = INVISIBLE;
                view1.visibility = INVISIBLE
            }
            SwitchElement.ALL -> view1.visibility = INVISIBLE
        }
    }

    fun setMin() {
        onElementClick(SwitchElement.MIN)
    }

    fun setNothing() {
        selectedElement = null
        for (view in rootLayout.children) {
            if (view is TextView) {
                setTvBackground(view, false)
            } else view.visibility = VISIBLE
        }
    }

    private fun setTvBackground(tv: TextView, isActive: Boolean) {
        val bgId =
            if (isActive)
                R.drawable.btn_switch_transaction
            else
                0
        tv.setBackgroundResource(bgId)
        tv.isActivated = isActive
    }

    enum class SwitchElement {
        MIN,
        HALF,
        ALL;

        fun getTv(root: SwitchExchange): TextView =
            when (this) {
                MIN -> root.tvMin
                HALF -> root.tvHalf
                ALL -> root.tvAll
            }

    }

    interface OnSwitchElementClickListener {
        fun onElementClicked(switchElement: SwitchElement)
    }
}