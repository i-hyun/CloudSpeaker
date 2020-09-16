package io.ihyun.cloudspeaker.extension

import android.app.Activity
import android.app.Service
import android.content.Intent
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

fun <T : Activity> Activity.startActivity(kClazz: KClass<T>, isFinish: Boolean = true, extras: Intent.() -> Unit = {}) {
    Intent(this, kClazz.java)
        .apply(extras)
        .let(::startActivity)

    if (isFinish) {
        finish()
    }
}

fun <T : Activity> Fragment.startActivity(kClazz: KClass<T>, isFinish: Boolean = true, extras: Intent.() -> Unit = {}) {
    activity?.startActivity(kClazz, isFinish, extras)
}

fun <T : Service> Activity.startService(kClazz: KClass<T>, extras: Intent.() -> Unit = {}) {
    Intent(this, kClazz.java)
        .apply(extras)
        .let(::startService)
}