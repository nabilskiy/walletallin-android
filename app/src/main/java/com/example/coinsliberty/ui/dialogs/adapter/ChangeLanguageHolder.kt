package com.example.coinsliberty.ui.dialogs.adapter

import android.annotation.SuppressLint
import android.net.sip.SipSession
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog
import com.example.coinsliberty.utils.extensions.invisible
import com.example.coinsliberty.utils.extensions.visibleIfOrInvisible
import kotlinx.android.synthetic.main.item_change_language.*
import kotlinx.android.synthetic.main.item_change_language.view.*


object ChangeLanguageHolder {

    class ItemHolder(private val onItemClick: (LanguageContent) -> Unit) :
        Holder<LanguageContent>() {

        private lateinit var listener : SipSession.Listener



        @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
        override fun bind(itemView: View, item: LanguageContent) {



           // itemView.setOnTouchListener { }

            if (item.active) { itemView.tvName.setTextColor(R.color.darkBlue) }
            itemView.ivCheck.visibleIfOrInvisible { item.active }

            itemView.ivFlag.setImageResource(item.ico)
            itemView.tvName.setText(item.name)
            itemView.rootView.setOnClickListener { onItemClick.invoke(item) }


        }
    }


}