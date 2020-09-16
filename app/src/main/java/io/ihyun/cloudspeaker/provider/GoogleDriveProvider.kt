package io.ihyun.cloudspeaker.provider

import android.app.Application
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import io.reactivex.rxjava3.core.Single

class GoogleDriveProvider(
    private val app: Application
) {

    private val scope get() = Scope(DriveScopes.DRIVE_READONLY)

    fun isConnected() = Single.fromCallable {

        GoogleSignIn.getLastSignedInAccount(app)
            ?.run { grantedScopes.contains(scope) }
            ?: false
    }!!

    fun requestConnect() = Single.fromCallable {

        val options = GoogleSignInOptions.Builder()
            .requestScopes(scope)
            .build()

        GoogleSignIn.getClient(app, options)
            .signInIntent
    }!!

    fun isConnect(data: Intent) = Single.fromCallable {

        GoogleSignIn.getSignedInAccountFromIntent(data)
            .isSuccessful
    }!!
}