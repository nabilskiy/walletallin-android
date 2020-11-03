package com.example.coinsliberty.utils.wallets

import com.example.coinsliberty.R

enum class Wallets {
    BITCOIN_WALLET{
        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_orange

        override fun getImg(): Int = R.drawable.bitcoin_logo
    },
    ETHEREUM_WALLET{
        override fun getImg(): Int = R.drawable.etherium_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_purple

    },
    LITECOIN_WALLET{
        override fun getImg(): Int = R.drawable.litecoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_grey
    },
    RIPPLE_WALLET{
        override fun getImg(): Int = R.drawable.ripple_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_blue
    },
    BITCOIN_CASH_WALLET{
        override fun getImg(): Int = R.drawable.bitcoin_cash_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_light_green
    },
    DASHCOIN_WALLET{
        override fun getImg(): Int = R.drawable.dashcoin_logo

        override fun getBackground(): Int = R.drawable.bg_wallet_item_middle_blue
    };
    abstract fun getImg(): Int
    abstract fun getBackground(): Int
}