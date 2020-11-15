package com.coinsliberty.wallet.utils.wallets

import android.os.Parcelable
import com.coinsliberty.wallet.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Wallets: Parcelable{
    BITCOIN_WALLET{
        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_orange

        override fun getImg(): Int = R.drawable.bitcoin_logo

        override fun getTitle(): String = "Bitcoin Wallet"
    },
    ETHEREUM_WALLET{
        override fun getImg(): Int = R.drawable.etherium_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_purple

        override fun getTitle(): String = "Etherium Wallet"

    },
    LITECOIN_WALLET{
        override fun getImg(): Int = R.drawable.litecoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_grey

        override fun getTitle(): String = "Litecoin Wallet"
    },
    RIPPLE_WALLET{
        override fun getImg(): Int = R.drawable.ripple_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_blue

        override fun getTitle(): String = "Ripple Wallet"
    },
    BITCOIN_CASH_WALLET{
        override fun getImg(): Int = R.drawable.bitcoin_cash_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_green

        override fun getTitle(): String = "Bitcoin Cash Wallet"
    },
    DASHCOIN_WALLET{
        override fun getImg(): Int = R.drawable.dashcoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_middle_blue

        override fun getTitle(): String = "Dashcoin Wallet"
    };
    abstract fun getImg(): Int
    abstract fun getBackground(): Int
    abstract fun getTitle(): String
}