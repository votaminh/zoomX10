package com.msc.zoom10.component.splash

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.view.View
import com.msc.zoom10.admob.NameRemoteAdmob
import com.msc.zoom10.BuildConfig
import com.msc.zoom10.admob.BannerAdmob
import com.msc.zoom10.admob.BaseAdmob
import com.msc.zoom10.admob.CollapsiblePositionType
import com.msc.zoom10.admob.InterAdmob
import com.msc.zoom10.base.activity.BaseActivity
import com.msc.zoom10.component.home.HomeActivity
import com.msc.zoom10.component.language.LanguageActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.msc.zoom10.databinding.ActivitySplashBinding
import com.msc.zoom10.utils.NativeAdmobUtils
import com.msc.zoom10.utils.NetworkUtil
import com.msc.zoom10.utils.SpManager

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var progressAnimator: ValueAnimator? = null

    @Inject
    lateinit var spManager: SpManager

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, SplashActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun onDestroy() {
        cancelLoadingListener()
        super.onDestroy()
    }

    private fun cancelLoadingListener() {
        progressAnimator?.removeAllListeners()
        progressAnimator?.cancel()
        progressAnimator = null
    }

    override fun onResume() {
        super.onResume()
        if (progressAnimator?.isPaused == true) {
            progressAnimator?.resume()
        }
    }

    override fun onPause() {
        progressAnimator?.pause()
        super.onPause()
    }

    override fun initViews() {
        if (spManager.getShowOnBoarding() && NetworkUtil.isOnline) {
            if (spManager.getBoolean(NameRemoteAdmob.NATIVE_LANGUAGE, true)) {
                NativeAdmobUtils.loadNativeLanguage()
            }
        }

        runProgress()
    }

    private fun runProgress() {
        gotoMainScreen()
    }

    private fun showBanner() {
//        if(spManager.getBoolean(NameRemoteAdmob.BANNER_SPLASH, true)){
//            val bannerAdmob = BannerAdmob(this, CollapsiblePositionType.NONE)
//            bannerAdmob.showBanner(this@SplashActivity, BuildConfig.banner_splash, viewBinding.banner)
//        }else{
//            viewBinding.banner.visibility = View.GONE
//        }
    }

    private fun gotoMainScreen() {
        cancelLoadingListener()
        if (spManager.getShowOnBoarding()) {
            LanguageActivity.start(this, true)
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        finish()
    }
}