package com.msc.demo_mvvm.component.home

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat
import com.msc.demo_mvvm.R
import com.msc.demo_mvvm.base.activity.BaseActivity
import com.msc.demo_mvvm.component.camera.CameraActivity
import com.msc.demo_mvvm.component.photo_collage.PhotoCollageActivity
import com.msc.demo_mvvm.component.setting.SettingActivity
import com.msc.demo_mvvm.databinding.ActivityMainBinding
import com.msc.demo_mvvm.utils.SpManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val REQUEST_PICKER_CONTACT = 211
        fun start(activity : Activity){
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setStatusBarColor(ContextCompat.getColor(this@HomeActivity, R.color.app_main), true)
        SpManager.getInstance(this).saveOnBoarding()

        viewBinding.run {
            tvExplore.setOnClickListener {
                CameraActivity.start(this@HomeActivity)
            }

            magnifier.setOnClickListener {
                CameraActivity.start(this@HomeActivity)
            }

            photoCollage.setOnClickListener {
                PhotoCollageActivity.start(this@HomeActivity)
            }

            setting.setOnClickListener {
                SettingActivity.start(this@HomeActivity)
            }
        }
    }
}