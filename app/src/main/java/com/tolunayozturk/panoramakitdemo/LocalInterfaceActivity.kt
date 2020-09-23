package com.tolunayozturk.panoramakitdemo

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.huawei.hms.panorama.Panorama
import com.huawei.hms.panorama.PanoramaInterface
import kotlinx.android.synthetic.main.activity_local_interface.*

class LocalInterfaceActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {

    companion object {
        private const val TAG: String = "LocalInterfaceActivity";
    }

    private lateinit var mLocalInterface: PanoramaInterface.PanoramaLocalInterface
    private var mIsGyroEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_interface)

        val intent = intent
        val uri = intent.data
        val type = intent.getIntExtra("PanoramaType", PanoramaInterface.IMAGE_TYPE_SPHERICAL)
        callLocalApi(uri, type)
    }

    private fun callLocalApi(imageUri: Uri?, imageType: Int) {
        mLocalInterface = Panorama.getInstance().getLocalInstance(this)

        if (mLocalInterface.init() == 0 && mLocalInterface.setImage(imageUri, imageType) == 0) {
            val view: View = mLocalInterface.view
            relativeLayout.addView(view)

            view.setOnTouchListener(this@LocalInterfaceActivity)
            changeInputButton.apply {
                bringToFront()
                setOnClickListener(this@LocalInterfaceActivity)
            }
        } else {
            Log.e(TAG, "local api error")
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.changeInputButton) {
            if (mIsGyroEnabled) {
                mIsGyroEnabled = false
                mLocalInterface.setControlMode(PanoramaInterface.CONTROL_TYPE_TOUCH)
            } else {
                mIsGyroEnabled = true
                mLocalInterface.setControlMode(PanoramaInterface.CONTROL_TYPE_POSE)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mLocalInterface.let {
            mLocalInterface.updateTouchEvent(event)
        }
        return true
    }
}