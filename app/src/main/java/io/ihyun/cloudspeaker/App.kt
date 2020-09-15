package io.ihyun.cloudspeaker

import android.app.Application
import io.ihyun.cloudspeaker.presentation.connect.ConnectViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(getModules())
        }
    }

    private fun getModules() = module {
        viewModel<ConnectViewModel>()
    }
}