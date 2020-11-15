package com.coinsliberty.wallet.utils.extensions

val Char.isCyrillicCharacter : Boolean
    get() {
        val block = Character.UnicodeBlock.of(this)

        return block == Character.UnicodeBlock.CYRILLIC ||
                block == Character.UnicodeBlock.CYRILLIC_SUPPLEMENTARY ||
                block == Character.UnicodeBlock.CYRILLIC_EXTENDED_A ||
                block == Character.UnicodeBlock.CYRILLIC_EXTENDED_B
    }