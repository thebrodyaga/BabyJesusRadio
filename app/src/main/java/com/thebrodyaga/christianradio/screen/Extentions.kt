package com.thebrodyaga.christianradio.screen

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView

fun View.defaultTopMarginWindowInsert() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = insets.systemWindowInsetTop
        v.layoutParams = params
        insets
    }
}

fun View.defaultBottomMarginWindowInsert() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = insets.systemWindowInsetBottom
        v.layoutParams = params
        insets
    }
}

fun ViewGroup.defaultOnApplyWindowInsetsListener() {
    setOnApplyWindowInsetsListener { _, insets ->
        forEach { it.dispatchApplyWindowInsets(insets) }
        insets
    }
}

fun RecyclerView.appbarBottomPadding() {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        val actionBarHeight =
            TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        clipToPadding = false
        setPadding(paddingLeft, paddingTop, paddingRight, actionBarHeight)
    }
}

fun View.appbarBottomPadding() {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        val actionBarHeight =
            TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        setPadding(paddingLeft, paddingTop, paddingRight, actionBarHeight)
    }
}

fun View.isInvisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

fun View.isGone(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.GONE else View.VISIBLE
}

fun Context.isSystemDarkMode(): Boolean? {
    return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        // Night mode is not active, we're using the light theme
        Configuration.UI_MODE_NIGHT_NO -> false
        // Night mode is active, we're using dark theme
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> null
    }
}