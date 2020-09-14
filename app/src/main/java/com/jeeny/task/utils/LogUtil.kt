/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.utils

import android.util.Log

object LogUtil {

    var isDebug = false
    var tag = "AFS_LOG"

    fun init(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun debug(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun debug(msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun info(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun info(msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun warning(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    fun warning(msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    fun error(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    fun error(msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }
}