package com.msc.demo_mvvm.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.msc.demo_mvvm.BuildConfig
import com.msc.demo_mvvm.R
import java.util.Locale


object AppEx {
    fun Activity.shareText(text : String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            this.getString(R.string.txt_share)
        )
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(
            intent,
            this.getString(R.string.txt_share)
        ))
    }

    fun Context.showPolicyApp() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://sites.google.com/view/blowercleanerandwatereject/trang-ch%E1%BB%A7")
        startActivity(openURL)
    }

    fun Context.openAppInStore() {
        val uri = Uri.parse("market://details?id=" + this.packageName)
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
        }
    }
    fun Context.shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        )
        startActivity(Intent.createChooser(intent, "choose one"))
    }
    fun Context.getDeviceLanguage(): String {
        return Locale.getDefault().language
    }

    fun Context.setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}