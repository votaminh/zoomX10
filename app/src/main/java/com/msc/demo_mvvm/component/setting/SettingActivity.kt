package com.msc.demo_mvvm.component.setting

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.msc.demo_mvvm.R
import com.msc.demo_mvvm.base.activity.BaseActivity
import com.msc.demo_mvvm.component.language.LanguageActivity
import com.msc.demo_mvvm.databinding.ActivitySettingBinding
import com.msc.demo_mvvm.utils.AppEx.openAppInStore
import com.msc.demo_mvvm.utils.AppEx.shareApp
import com.msc.demo_mvvm.utils.AppEx.showPolicyApp

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setStatusBarColor(ContextCompat.getColor(this@SettingActivity, R.color.white), true)

        viewBinding.run {
            btnShare.setOnClickListener { shareApp() }
            btnLanguage.setOnClickListener {
                LanguageActivity.start(this@SettingActivity, false)
            }
            btnPrivacyPolicy.setOnClickListener { showPolicyApp() }
            btnRateUs.setOnClickListener { openAppInStore() }
        }
    }
}