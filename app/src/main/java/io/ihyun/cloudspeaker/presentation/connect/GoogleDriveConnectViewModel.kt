package io.ihyun.cloudspeaker.presentation.connect

import android.content.Intent
import io.ihyun.cloudspeaker.extension.onToMain
import io.ihyun.cloudspeaker.presentation.base.BaseViewModel
import io.ihyun.cloudspeaker.provider.GoogleDriveProvider
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GoogleDriveConnectViewModel(
    private val provider: GoogleDriveProvider
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
    
    fun request() = provider.requestConnect()

    fun responseHandler(data: Intent) {

        provider.isConnect(data)
            .subscribeBy(onSuccess = connected::onNext)
            .collect()
    }
}