package com.example.coinsliberty.ui.dialogs.adapter

import android.os.Build
import android.text.Html
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.extensions.invisible
import kotlinx.android.synthetic.main.dialog_change_language.view.*
import kotlinx.android.synthetic.main.item_change_language.view.*
import java.lang.Exception


object ChangeLanguageHolder {
    class CommonStarHolder() : Holder<LanguageContent>() {

        val adapter = BaseAdapter().map(R.layout.item_change_language, ItemHolder())

        override fun bind(itemView: View, item: LanguageContent) {
            itemView.rvChangeLanguage.adapter = adapter


                    adapter.itemsLoaded()


        }
    }

    class ItemHolder : Holder<LanguageContent>() {
        override fun bind(itemView: View, item: LanguageContent) {
            if (!item.active) {
                itemView.rvChangeLanguage.ivCheck.invisible()
            }
            itemView.rvChangeLanguage.ivFlag.setImageResource(item.ico)
            itemView.rvChangeLanguage.tvName.setText(item.name)
        }
    }



//    class PriceHolder : Holder<PriceInfoContent>() {
//
//        override fun bind(itemView: View, item: PriceInfoContent) {
//            Glide
//                .with(itemView.context)
//                .load(MarketLogoTools.values().first { it.getValue() == item.marketName }
//                    .getImage())
//                .centerInside()
//                .into(itemView.ivMarketIcon)
//            itemView.tvPriceName.text = item.originalProductName
//            itemView.tvPrice.text = item.priceValue.toString() + " грн."
//            try {
//                val availability =
//                    Availability.values().first { it.getIndex() == item.availability }
//                itemView.tvAvailablePrice.text = itemView.context.getString(availability.getName())
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    itemView.tvAvailablePrice.setTextColor(itemView.context.getColor(availability.getTextColor()))
//                } else {
//                    itemView.tvAvailablePrice.setTextColor(
//                        itemView.context.resources.getColor(
//                            availability.getTextColor()
//                        )
//                    )
//                }
//            } catch (e: Exception) {
//
//            }
//
//        }
//
//    }

}