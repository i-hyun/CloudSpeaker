package io.ihyun.cloudspeaker

import android.app.Application
import io.ihyun.cloudspeaker.data.OneDriveClient
import io.ihyun.cloudspeaker.presentation.connect.GoogleDriveConnectViewModel
import io.ihyun.cloudspeaker.presentation.connect.OneDriveConnectViewModel
import io.ihyun.cloudspeaker.provider.DropBoxProvider
import io.ihyun.cloudspeaker.provider.GoogleDriveProvider
import io.ihyun.cloudspeaker.provider.OneDriveProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidFileProperties()
            modules(getModules())
        }
    }

    private fun getModules() = module {
        factory<GoogleDriveProvider>()
        single<OneDriveClient>()
        factory<OneDriveProvider>()
        factory<DropBoxProvider>()

        viewModel<GoogleDriveConnectViewModel>()
        viewModel<OneDriveConnectViewModel>()
    }
}