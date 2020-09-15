package io.ihyun.cloudspeaker.extension

import android.util.Log

fun Any.log(message: String?) {
    Thread.currentThread().stackTrace
        .find { it.className.contains(javaClass.simpleName) }
        ?.let { Log.d("DEBUG_LOG", "(${it.fileName}:${it.lineNumber}) :: $message") }
}