package com.tolunayozturk.panoramakitdemo

import android.content.Context
import com.huawei.hms.panorama.PanoramaInterface
import com.huawei.hms.support.api.client.ResultCallback

class ResultCallbackImpl(context: Context) : ResultCallback<PanoramaInterface.ImageInfoResult> {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun onResult(result: PanoramaInterface.ImageInfoResult?) {
        result?.let {
            if (result.status.isSuccess) {
                val intent = result.imageDisplayIntent
                intent?.let { context?.startActivity(intent) }
            }
        }
    }
}