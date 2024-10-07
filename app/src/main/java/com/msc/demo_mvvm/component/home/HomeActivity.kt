package com.msc.demo_mvvm.component.home

import android.app.Activity
import android.content.Intent
import com.msc.demo_mvvm.base.activity.BaseActivity
import com.msc.demo_mvvm.camera.CameraActivity
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
        CameraActivity.start(this@HomeActivity)
        SpManager.getInstance(this).saveOnBoarding()
    }
}