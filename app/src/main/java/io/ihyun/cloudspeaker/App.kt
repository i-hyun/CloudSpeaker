package io.ihyun.cloudspeaker

import android.app.Application
import io.ihyun.cloudspeaker.presentation.connect.ConnectViewModel
import io.ihyun.cloudspeaker.provider.GoogleDriveProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.factory

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

        viewModel<ConnectViewModel>()
    }
}