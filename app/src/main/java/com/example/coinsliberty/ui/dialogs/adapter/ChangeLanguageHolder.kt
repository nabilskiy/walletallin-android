package com.example.coinsliberty.ui.dialogs.adapter

import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog
import com.example.coinsliberty.utils.extensions.invisible
import com.example.coinsliberty.utils.extensions.visibleIfOrInvisible
import kotlinx.android.synthetic.main.item_change_language.view.*


object ChangeLanguageHolder {

    class ItemHolder(private val onItemClick: (LanguageContent) -> Unit) :
        Holder<LanguageContent>() {
        override fun bind(itemView: View, item: LanguageContent) {
//            itemView.setOnTouchListener { view, event ->
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        view.isSelected = true
//                        ChangeLanguageDialog.currentLanguageContent = item
//                        Handler().postDelayed({view.isSelected = false}, 300)
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        view.isSelected = false
//
//                        onItemClick.invoke(item)
//                    }
//                }
//                true
//            }
           // itemView.setOnTouchListener { v, event ->  }
            itemView.ivCheck.visibleIfOrInvisible { item.active }
            itemView.ivFlag.setImageResource(item.ico)
            itemView.tvName.setText(item.name)
            itemView.rootView.setOnClickListener {
              //  ChangeLanguageDialog.currentLanguageContent = item
                onItemClick.invoke(item)
            }
        }
    }


}