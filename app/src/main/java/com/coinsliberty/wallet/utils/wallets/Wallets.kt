package com.coinsliberty.wallet.utils.wallets

import android.graphics.Color
import android.os.Parcelable
import com.coinsliberty.wallet.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Wallets : Parcelable {
    BITCOIN_WALLET {
        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_orange

        override fun getImg(): Int = R.drawable.bitcoin_logo

        override fun getTitle(): String = "Bitcoin Wallet"

        override fun getColor(): Int =
            Color.parseColor("#f2a900")

        override fun getFeeCoefficient() = 0.00000001 * 240

    },
    ETHEREUM_WALLET {
        override fun getImg(): Int = R.drawable.etherium_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_purple

        override fun getTitle(): String = "Etherium Wallet"

        override fun getColor(): Int =
            Color.parseColor("#8198EE")

        override fun getFeeCoefficient(): Double = 2100 * 10E-9

    },
    LITECOIN_WALLET {
        override fun getImg(): Int = R.drawable.litecoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_grey

        override fun getTitle(): String = "Litecoin Wallet"

        override fun getColor(): Int =
            Color.parseColor("#d3d3d3")

        override fun getFeeCoefficient(): Double = 1.0

    },
    RIPPLE_WALLET {
        override fun getImg(): Int = R.drawable.ripple_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_blue

        override fun getTitle(): String = "Ripple Wallet"

        override fun getColor(): Int =
            Color.parseColor("#006097")


        override fun getFeeCoefficient(): Double = 1.0
    },
    BITCOIN_CASH_WALLET {
        override fun getImg(): Int = R.drawable.bitcoin_cash_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_green

        override fun getTitle(): String = "Bitcoin Cash Wallet"

        override fun getColor(): Int =
            Color.parseColor("#ee8c28")

        override fun getFeeCoefficient(): Double = 1.0
    },
    DASHCOIN_WALLET {
        override fun getImg(): Int = R.drawable.dashcoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_middle_blue

        override fun getTitle(): String = "Dashcoin Wallet"

        override fun getColor(): Int =
            Color.parseColor("#006097")

        override fun getFeeCoefficient(): Double = 1.0
    };

    abstract fun getImg(): Int
    abstract fun getBackground(): Int
    abstract fun getTitle(): String
    abstract fun getColor(): Int
    abstract fun getFeeCoefficient(): Double
}