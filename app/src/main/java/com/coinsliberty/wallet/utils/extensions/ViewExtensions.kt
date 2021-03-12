package com.coinsliberty.wallet.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visibleIfOrGone(predicate: () -> Boolean) {
    if (predicate()) visible() else gone()
}

fun View.visibleIfOrInvisible(predicate: () -> Boolean) {
    if (predicate()) visible() else invisible()
}

fun View.disable() {
    alpha = 0.7f
    isClickable = false
    isEnabled = false
}

fun View.enable() {
    alpha = 1f
    isClickable = true
    isEnabled = true
}

fun View.enableIf(predicate: () -> Boolean) {
    if (predicate()) enable() else disable()
}

fun RecyclerView.addScrollListenerForDownView(downView: View, currentHeight: Float = 0f) : FloatingScrollListener {
    val listener = FloatingScrollListener(downView, currentHeight)
    addOnScrollListener(listener)
    return listener
}

fun hideKeyboard(activity: Activity?) {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

class FloatingScrollListener(private val downView: View,
                             var currentHeight: Float = 0f): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(dy > 0) {
            if(currentHeight.plus(dy) <= downView.height) currentHeight += dy
            else currentHeight = downView.height.toFloat()
        } else {
            if(currentHeight + dy >= 0) currentHeight += dy
            else currentHeight = 0f
        }

        downView.translationY = currentHeight
    }

}