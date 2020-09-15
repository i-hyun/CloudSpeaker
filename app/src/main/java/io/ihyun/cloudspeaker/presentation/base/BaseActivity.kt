package io.ihyun.cloudspeaker.presentation.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<VB : ViewDataBinding>(
    @LayoutRes private val res: Int
) : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    protected lateinit var binder: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (res != 0) {
            binder = DataBindingUtil.setContentView<VB>(this, res)
                .also { it.lifecycleOwner = this }
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected fun Disposable.collect() {
        compositeDisposable.add(this)
    }
}