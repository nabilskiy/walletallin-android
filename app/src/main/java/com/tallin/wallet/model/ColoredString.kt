package com.tallin.wallet.model

data class ColoredString(
    val text: String,
    val setting: ArrayList<ColoredSpan>
)

data class ColoredSpan(
    val start: Int,
    val end: Int,
    val color: Int
)


