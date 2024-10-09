package com.msc.zoom10.component.ump

import com.msc.zoom10.base.activity.BaseActivity
import com.msc.zoom10.component.splash.SplashActivity
import com.msc.zoom10.App
import com.msc.zoom10.databinding.ActivitySplashBinding
import com.msc.zoom10.utils.RemoteConfig
import com.msc.zoom10.utils.SpManager
import com.msc.zoom10.utils.UMPUtils


class UMPActivity : BaseActivity<ActivitySplashBinding>() {
    private val TAG = "ump_activity"

    override fun provideViewBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    override fun initData() {
        super.initData()

        if(SpManager.getInstance(this).isUMPShowed()){
            RemoteConfig.instance().fetch()
            openSplash();
        }else{
            RemoteConfig.instance().fetch{
                initUmp()
            }
        }
    }

    private fun openSplash() {

        val app : App = application as App
        app.initAds()

        SpManager.getInstance(this).setUMPShowed(true)
        SplashActivity.start(this);
        finish()
    }

    private fun initUmp() {
        UMPUtils.init(this@UMPActivity, nextAction = {
            openSplash()
        }, true, false)
    }
}