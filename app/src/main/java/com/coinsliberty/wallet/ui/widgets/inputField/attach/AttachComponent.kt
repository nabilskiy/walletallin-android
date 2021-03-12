package com.coinsliberty.wallet.ui.widgets.inputField.attach

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import com.coinsliberty.wallet.R
import kotlinx.android.synthetic.main.attach_component.view.*

class AttachComponent  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initView()
        readAttributes(attrs)
    }

    private fun initView() {
        inflate(context, R.layout.attach_component , this)
    }

    private fun readAttributes(attrs: AttributeSet?) {
        attrs ?: return

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AttachComponent, 0, 0)

        val fieldText = typedArray.getString(R.styleable.AttachComponent_ac_text_field_text) ?: ""
        tvAttachFile.text = fieldText

//        val attachButtonText = typedArray.getString(R.styleable.AttachComponent_ac_button_text) ?: ""
//        tvAttachButton.text = attachButtonText

        chooseFile()
        typedArray.recycle()
    }

    fun chooseFile() {
//        tvAttachButton.setOnClickListener {
//            Toast.makeText(context, "SMTH", Toast.LENGTH_SHORT).show()
//        }
    }

}

