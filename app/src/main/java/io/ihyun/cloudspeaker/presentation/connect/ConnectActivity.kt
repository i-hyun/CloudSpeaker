package io.ihyun.cloudspeaker.presentation.connect

import android.content.Intent
import android.os.Bundle
import io.ihyun.cloudspeaker.R
import io.ihyun.cloudspeaker.databinding.ActivityConnectBinding
import io.ihyun.cloudspeaker.extension.clickWithThrottle
import io.ihyun.cloudspeaker.extension.log
import io.ihyun.cloudspeaker.presentation.base.BaseActivity
import io.ihyun.cloudspeaker.presentation.common.AlertBuilder
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectActivity : BaseActivity<ActivityConnectBinding>(
    R.layout.activity_connect
) {

    companion object {
        private const val REQUEST_CODE_FOR_GOOGLE_DRIVE = 8472
    }

    private val vm by viewModel<ConnectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.observeConnected()
            .subscribeBy { test() }
            .collect()

        vm.observeAlertMessage()
            .subscribeBy(onNext = ::showAlert)
            .collect()

        binder.btnConnectGoogleDrive.clickWithThrottle()
            .flatMapSingle { vm.requestGoogleDrive() }
            .subscribeBy { startActivityForResult(it, REQUEST_CODE_FOR_GOOGLE_DRIVE) }
            .collect()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK || data == null) return

        when (requestCode) {
            REQUEST_CODE_FOR_GOOGLE_DRIVE -> vm.responseGoogleDrive(data)
        }
    }

    private fun showAlert(message: String) {

        AlertBuilder(this, message)
            .show()
    }

    private fun test() {
        log("HJLEE")
    }
}