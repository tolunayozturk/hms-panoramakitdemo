package com.tolunayozturk.panoramakitdemo

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.huawei.hms.panorama.Panorama
import com.huawei.hms.panorama.PanoramaInterface

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    private lateinit var loadWithoutTypeButton: Button
    private lateinit var loadWithTypeButton: Button
    private lateinit var loadWithLocalInterfaceButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadWithoutTypeButton = findViewById(R.id.loadWithoutTypeButton)
        loadWithTypeButton = findViewById(R.id.loadWithTypeButton)
        loadWithLocalInterfaceButton = findViewById(R.id.loadWithLocalInterfaceButton)

        /*
        This is just for demo purposes. In your app, handle permissions properly.
        For this demo, this is not needed but in a real app you might want to
        load an image from user's photo library.
         */
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, 1)

        loadWithoutTypeButton.setOnClickListener { loadImageWithoutType() }
        loadWithTypeButton.setOnClickListener { loadImageWithType() }
        loadWithLocalInterfaceButton.setOnClickListener { panoramaWithLocalInterface() }
    }

    private fun loadImageWithoutType() {
        Panorama.getInstance().loadImageInfoWithPermission(
            this,
            Uri.parse("android.resource://$packageName/${R.raw.pano2}")
        )
            .setResultCallback(ResultCallbackImpl(this@MainActivity))
    }

    private fun loadImageWithType() {
        Panorama.getInstance().loadImageInfoWithPermission(
            this,
            Uri.parse("android.resource://$packageName/${R.raw.pano2}"),
            PanoramaInterface.IMAGE_TYPE_RING
        ).setResultCallback(ResultCallbackImpl(this@MainActivity))
    }

    private fun panoramaWithLocalInterface() {
        val intent = Intent(this@MainActivity, LocalInterfaceActivity::class.java)
        intent.apply {
            data = Uri.parse("android.resource://$packageName/${R.raw.pano1}");
            putExtra("PanoramaType", PanoramaInterface.IMAGE_TYPE_SPHERICAL)
        }
        startActivity(intent)
    }
}