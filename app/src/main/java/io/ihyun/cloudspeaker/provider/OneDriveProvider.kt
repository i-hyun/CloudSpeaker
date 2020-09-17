package io.ihyun.cloudspeaker.provider

import android.app.Activity
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.exception.MsalException
import io.ihyun.cloudspeaker.data.OneDriveClient
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeEmitter

class OneDriveProvider(
    private val oneDriveClient: OneDriveClient
) {

    fun isConnected() = oneDriveClient.getAccount()
        .flatMapMaybe {

            Maybe.create<Boolean> { emit ->

                val authority = it.configuration.defaultAuthority.authorityURL.toString()

                it.acquireTokenSilentAsync(oneDriveClient.scope, authority, bindAccountCallback(emit))
            }
        }!!

    fun requestConnect(activity: Activity) = oneDriveClient.getAccount()
        .flatMapMaybe {

            Maybe.create<Boolean> { emit ->
                it.signIn(activity, "", oneDriveClient.scope, bindAccountCallback(emit))
            }
        }!!

    private fun bindAccountCallback(emit: MaybeEmitter<Boolean>) = object : AuthenticationCallback {

        override fun onSuccess(result: IAuthenticationResult) {
            oneDriveClient.setAccessToken(result.accessToken)
            emit.onSuccess(true)
        }

        override fun onError(exception: MsalException?) {
            emit.onSuccess(false)
        }

        override fun onCancel() {
            emit.onComplete()
        }
    }
}