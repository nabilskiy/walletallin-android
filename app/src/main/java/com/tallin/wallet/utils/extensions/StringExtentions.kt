package com.tallin.wallet.utils.extensions

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import java.util.*

fun String.formatFromHtml(): Spanned? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }
    else -> {
        Html.fromHtml(this)
    }
}

fun String.isStartWith(vararg values: String, ignoreCase: Boolean = true): Boolean {
    values.forEach {
        if (startsWith(it, ignoreCase)) return true
    }

    return false
}


fun String?.removeInnerSpaces() = this?.replace(" ", "")

fun String?.getValueOrPlaceholder(placeholder: String = "---") =
    if (this.isNullOrEmpty()) placeholder else this

val String?.containsCyrillic: Boolean
    get() {
        if (this == null) return false

        for (c in this) {
            if (c.isCyrillicCharacter) return true
        }
        return false
    }

val String?.isAllSame: Boolean
    get() {
        if (this.isNullOrEmpty()) return false

        this.forEach { if (it != this[0]) return false }

        return true
    }

fun SpannableString.setCustomHandleLinks(listener: (startPosition: Int, endPosition: Int, url: String) -> Unit): SpannableString {

    getSpans(0, length, URLSpan::class.java).forEach {
        val startPosition = getSpanStart(it)
        val endPosition = getSpanEnd(it)
        val url = it.url

        removeSpan(it)
        listener(startPosition, endPosition, url)
    }
    return this
}