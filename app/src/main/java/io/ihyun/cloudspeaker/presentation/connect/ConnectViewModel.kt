package io.ihyun.cloudspeaker.presentation.connect

import android.content.Intent
import io.ihyun.cloudspeaker.extension.onToMain
import io.ihyun.cloudspeaker.presentation.base.BaseViewModel
import io.ihyun.cloudspeaker.provider.GoogleDriveProvider
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.CompletableSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class ConnectViewModel(
    private val googleDriveProvider: GoogleDriveProvider
) : BaseViewModel() {

    private val alertMessage = PublishSubject.create<String>()
    private val connectedGoogleDrive = CompletableSubject.create()

    fun observeAlertMessage() = alertMessage.onToMain()
    fun observeConnected() = connectedGoogleDrive.onToMain()
        .doOnSubscribe { checkConnected() }!!

    private fun checkConnected() {

        googleDriveProvider.isConnected()
            .filter { it }
            .subscribeBy { connectedGoogleDrive.onComplete() }
            .collect()
    }

    fun requestGoogleDrive() = googleDriveProvider.requestConnect()

    fun responseGoogleDrive(data: Intent) {

        googleDriveProvider.isConnect(data)
            .subscribeBy {
                if (it) connectedGoogleDrive.onComplete()
                else alertMessage.onNext("google drive connect fail")
            }
            .collect()
    }
}