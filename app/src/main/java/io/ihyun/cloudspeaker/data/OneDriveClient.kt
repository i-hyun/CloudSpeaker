package io.ihyun.cloudspeaker.data

import android.app.Application
import com.microsoft.graph.authentication.IAuthenticationProvider
import com.microsoft.graph.requests.extensions.GraphServiceClient
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.exception.MsalException
import io.ihyun.cloudspeaker.R
import io.reactivex.rxjava3.core.Single

class OneDriveClient(
    private val app: Application
) {

    val scope = arrayOf("User.Read", "Files.Read")
    private var accessToken = ""

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun getAccount() = Single.create<ISingleAccountPublicClientApplication> { emit ->

        val listener = object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {

            override fun onCreated(application: ISingleAccountPublicClientApplication) {
                emit.onSuccess(application)
            }

            override fun onError(exception: MsalException) {
                emit.onError(exception)
            }
        }

        PublicClientApplication.createSingleAccountPublicClientApplication(app, R.raw.msal_config, listener)
    }!!

    fun build() = Single.fromCallable {

        GraphServiceClient.builder()
            .authenticationProvider(authenticationProvider)
            .buildClient()
    }!!

    private val authenticationProvider = IAuthenticationProvider {
        it.addHeader("Authorization", "Bearer $accessToken");
    }
}