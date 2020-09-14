/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

@file:Suppress("DEPRECATION")

package com.jeeny.task.utils.extensions

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            Log.i("Service status", "Running")
            return true
        }
    }
    Log.i("Service status", "Not running")
    return false
}