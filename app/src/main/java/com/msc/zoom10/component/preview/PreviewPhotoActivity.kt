package com.msc.zoom10.component.preview

import android.app.Activity
import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Environment
import android.os.FileUtils
import androidx.work.impl.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.msc.zoom10.R
import com.msc.zoom10.base.activity.BaseActivity
import com.msc.zoom10.databinding.ActivityPreviewPhotoBinding
import com.msc.zoom10.utils.AppEx.copyFile
import com.msc.zoom10.utils.DialogEx.showDialogDelete
import com.msc.zoom10.utils.PermissionUtils
import java.io.File

class PreviewPhotoActivity : BaseActivity<ActivityPreviewPhotoBinding>() {

    companion object {
        private const val KEY_PHOTO_PATH = "KEY_PHOTO_PATH"

        fun start(activity : Activity, path : String){
            activity.startActivity(Intent(activity, PreviewPhotoActivity::class.java).run {
                putExtra(KEY_PHOTO_PATH, path)
            })
        }
    }

    override fun provideViewBinding(): ActivityPreviewPhotoBinding {
        return ActivityPreviewPhotoBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()

        val path = intent.getStringExtra(KEY_PHOTO_PATH)

        viewBinding.run {
            Glide.with(this@PreviewPhotoActivity).load(path).into(imv)

            imvBack.setOnClickListener {
                finish()
            }

            imvDelete.setOnClickListener {
                showDialogDelete{
                    if (path != null) {
                        File(path).delete()
                    }
                    finish()
                }
            }

            tvSave.setOnClickListener {
                if(PermissionUtils.storageGrantJustImage(this@PreviewPhotoActivity)){
                    val srcpath = path
                    val desPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/" + System.currentTimeMillis() + ".jpeg"
                    srcpath?.let {
                        it1 -> copyFile(it1, desPath)
                        MediaScannerConnection.scanFile(this@PreviewPhotoActivity, arrayOf(desPath), null) { path, uri ->
                            showToast(getString(R.string.txt_saved_image_at) + desPath)
                        }
                    }
                }else{
                    PermissionUtils.requestStorage(this@PreviewPhotoActivity, 432)
                }
            }
        }
    }

}