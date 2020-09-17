package io.ihyun.cloudspeaker.presentation.connect

import android.app.Activity
import io.ihyun.cloudspeaker.extension.onToMain
import io.ihyun.cloudspeaker.presentation.base.BaseViewModel
import io.ihyun.cloudspeaker.provider.OneDriveProvider
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject

class OneDriveConnectViewModel(
    private val provider: OneDriveProvider
) : BaseViewModel() {

    private val connected = BehaviorSubject.create<Boolean>()

    init {
        provider.isConnected()
            .subscribeBy(onSuccess = connected::onNext)
            .collect()
    }

    fun observeConnected() = connected.onToMain()
        .filter { it }
        .map { Unit }!!

    fun observeUnconnected() = connected.onToMain()
        .filter { !it }
        .map { Unit }!!

    fun request(activity: Activity) {

        provider.requestConnect(activity)
            .subscribeBy(onSuccess = connected::onNext)
            .collect()
    }
}