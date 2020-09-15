package io.ihyun.cloudspeaker.extension

import android.view.View
import com.jakewharton.rxbinding3.view.clicks

private var lastClickTime = 0L

private fun checkDelay(): Boolean {
    val clickDelay = 300L
    val time = System.currentTimeMillis() - lastClickTime

    return clickDelay < time
}

private fun delaySync() {
    lastClickTime = System.currentTimeMillis()
}

fun View.clickWithThrottle() = clicks()
    .filter { checkDelay() }
    .doOnNext { delaySync() }!!