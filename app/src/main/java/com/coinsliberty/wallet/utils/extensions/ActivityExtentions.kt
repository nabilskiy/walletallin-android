package com.coinsliberty.wallet.utils.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.toast(@StringRes idMessage: Int) {
    Toast.makeText(this, idMessage, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String, isAddToStack: Boolean = false) {
    val transaction = supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment, tag)

    if(isAddToStack) transaction.addToBackStack(null)

    transaction.commit()
}

fun FragmentActivity.setupFullScreen() {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun FragmentActivity.setupLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun FragmentActivity.setupDefaultStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = 0
}

fun FragmentActivity.setTransparentLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT

        }
    }
}

fun FragmentActivity.setTransparentDarkStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.TRANSPARENT
        }
    }
}


fun FragmentActivity.getRootView(): View {
    return findViewById(android.R.id.content)
}
fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}
fun FragmentActivity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun FragmentActivity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}