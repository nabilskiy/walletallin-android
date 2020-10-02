package com.example.coinsliberty.utils.extensions

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.getCompatDrawable(@DrawableRes idDrawable: Int) = ContextCompat.getDrawable(this, idDrawable)

fun Context.getCompatColor(@ColorRes idColor: Int) = ContextCompat.getColor(this, idColor)

fun Context.getCompatFont(@FontRes idFont: Int) = ResourcesCompat.getFont(this, idFont)

fun Context.getCompatColorStateList(@ColorRes idColor: Int) = ContextCompat.getColorStateList(this, idColor)

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, message, duration).show()

fun Context.getColorFromAttr(@AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

