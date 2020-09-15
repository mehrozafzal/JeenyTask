/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */
package com.jeeny.task.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import java.io.IOException

object CommonUtils {
    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isPackageInstalled(packageName: String?, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}