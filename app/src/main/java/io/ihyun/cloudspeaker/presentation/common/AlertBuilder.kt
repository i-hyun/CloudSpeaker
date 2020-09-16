package io.ihyun.cloudspeaker.presentation.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class AlertBuilder : AlertDialog.Builder {

    constructor(context: Context, message: String) : super(context) {
        setMessage(message)
    }

    constructor(context: Context, @StringRes res: Int) : super(context) {
        setMessage(res)
    }

    init {
        setCancelable(false)
        setPositive()
    }

    fun setPositive(s: String = "확인", delegate: (() -> Unit)? = null): AlertBuilder {
        setPositiveButton(s) { _, _ -> delegate?.invoke() }
        return this
    }

    fun setNegative(s: String = "취소", delegate: (() -> Unit)? = null): AlertBuilder {
        setNegativeButton(s) { _, _ -> delegate?.invoke() }
        return this
    }
}