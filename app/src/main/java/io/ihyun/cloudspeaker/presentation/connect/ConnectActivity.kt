package io.ihyun.cloudspeaker.presentation.connect

import android.content.Intent
import android.os.Bundle
import io.ihyun.cloudspeaker.R
import io.ihyun.cloudspeaker.databinding.ActivityConnectBinding
import io.ihyun.cloudspeaker.extension.clickWithThrottle
import io.ihyun.cloudspeaker.extension.startActivity
import io.ihyun.cloudspeaker.presentation.base.BaseActivity
import io.ihyun.cloudspeaker.presentation.main.MainActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectActivity : BaseActivity<ActivityConnectBinding>(
    R.layout.activity_connect
) {

    companion object {
        private const val REQUEST_CODE_FOR_GOOGLE_DRIVE = 8472
    }

    private val googleDriveVm by viewModel<GoogleDriveConnectViewModel>()
    private val oneDriveVm by viewModel<OneDriveConnectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.ambArray(
            googleDriveVm.observeConnected(),
            oneDriveVm.observeConnected()
        ).subscribe { startActivity(MainActivity::class) }
            .collect()

        binder.btnConnectGoogleDrive.clickWithThrottle()
            .flatMapSingle { googleDriveVm.request() }
            .subscribeBy { startActivityForResult(it, REQUEST_CODE_FOR_GOOGLE_DRIVE) }
            .collect()

        binder.btnConnectOneDrive.clickWithThrottle()
            .subscribeBy { oneDriveVm.request(this) }
            .collect()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK || data == null) return

        when (requestCode) {
            REQUEST_CODE_FOR_GOOGLE_DRIVE -> googleDriveVm.responseHandler(data)
        }
    }
}