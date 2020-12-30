package com.jsbs87.android.app.mapsmeep.presentation.extension

import android.view.View

fun View?.visible(visible: Boolean) {
    if (visible) this.visible()
    else this.gone()
}

fun View?.visible() {
    this ?: return
    this.visibility = View.VISIBLE
}

fun View?.invisible() {
    this ?: return
    this.visibility = View.INVISIBLE
}

fun View?.gone() {
    this ?: return
    this.visibility = View.GONE
}


