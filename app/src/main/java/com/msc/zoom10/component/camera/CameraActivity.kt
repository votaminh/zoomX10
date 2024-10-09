package com.msc.zoom10.component.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.SeekBar
import androidx.annotation.OptIn
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.video.ExperimentalVideo
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.msc.zoom10.R
import com.msc.zoom10.base.activity.BaseActivity
import com.msc.zoom10.component.photo_collage.PhotoCollageActivity
import com.msc.zoom10.databinding.ActivityCameraBinding
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private val executor = Executors.newSingleThreadExecutor()
    private val permissions = listOf(Manifest.permission.CAMERA)
    private val permissionsRequestCode = Random.nextInt(0, 10000)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var camera : Camera? = null
    private var imageCapture : ImageCapture? = null

    companion object {
        fun start(activity : Activity){
            activity.startActivity(Intent(activity, CameraActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivityCameraBinding {
        return ActivityCameraBinding.inflate(layoutInflater)
    }

    @OptIn(ExperimentalVideo::class)
    override fun initViews() {
        super.initViews()
        setStatusBarColor(ContextCompat.getColor(this@CameraActivity, R.color.app_main), true)
        viewBinding.run {
            sbZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    camera?.cameraControl?.setLinearZoom(p1/10f)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            flash.setOnClickListener {
                camera?.cameraControl?.enableTorch(!flash.isSelected)
                flash.isSelected = !flash.isSelected

//                if(flash.isSelected){
//                    flash.animate().alpha(1f).start()
//                }else{
//                    flash.animate().alpha(0.4f).start()
//                }
            }

            capture.setOnClickListener {
                val photoFolder = File(filesDir, "photo")
                if(!photoFolder.exists()){
                    photoFolder.mkdirs()
                }
                val file = File(photoFolder, System.currentTimeMillis().toString() + ".jpg")

                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                imageCapture?.takePicture(
                    outputFileOptions,
                    ContextCompat.getMainExecutor(this@CameraActivity),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            showToast(getString(R.string.txt_saved_image))
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.message?.let { it1 -> showToast(it1) }
                        }
                    }
                )
            }

            collage.setOnClickListener {
                PhotoCollageActivity.start(this@CameraActivity)
            }

            switchCamera.setOnClickListener {
                if(lensFacing == CameraSelector.LENS_FACING_BACK){
                    lensFacing = CameraSelector.LENS_FACING_FRONT
                }else{
                    lensFacing = CameraSelector.LENS_FACING_BACK
                }

                bindCameraUseCases()
            }
        }
    }

    override fun onDestroy() {
        executor.apply {
            shutdown()
            awaitTermination(1000, TimeUnit.MILLISECONDS)
        }

        super.onDestroy()
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindCameraUseCases() = viewBinding.viewFinder.post {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(viewBinding.viewFinder.display.rotation)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(viewBinding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(viewBinding.viewFinder.display.rotation)
                .build()


            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
            cameraProvider.unbindAll()

            camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageAnalysis, imageCapture)

            preview.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onResume() {
        super.onResume()
        if (!hasPermissions(this)) {
            ActivityCompat.requestPermissions(
                this, permissions.toTypedArray(), permissionsRequestCode)
        } else {
            bindCameraUseCases()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequestCode && hasPermissions(this)) {
            bindCameraUseCases()
        } else {
            finish()
        }
    }

    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}