package io.ihyun.cloudspeaker.presentation.connect

import android.os.Bundle
import io.ihyun.cloudspeaker.R
import io.ihyun.cloudspeaker.databinding.ActivityConnectBinding
import io.ihyun.cloudspeaker.extension.clickWithThrottle
import io.ihyun.cloudspeaker.presentation.base.BaseActivity

class ConnectActivity : BaseActivity<ActivityConnectBinding>(
    R.layout.activity_connect
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder.btnConnectGoogleDrive.clickWithThrottle()
            .subscribe()
            .collect()
    }
}