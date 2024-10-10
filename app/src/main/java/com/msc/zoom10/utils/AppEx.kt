package com.msc.zoom10.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.msc.zoom10.BuildConfig
import com.msc.zoom10.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale
import java.util.Objects


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
        openURL.data = Uri.parse("https://sites.google.com/view/phone-magnifier-privacy-policy/trang-ch%E1%BB%A7")
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

    fun Activity.copyFile(srcPath: String, desPath: String): Boolean {
        return try {
            val srcFile = File(srcPath)
            val desFile = File(desPath)

            if (!srcFile.exists()) {
                return false
            }

            FileInputStream(srcFile).use { inputStream ->
                FileOutputStream(desFile).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int

                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }
            }

            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}