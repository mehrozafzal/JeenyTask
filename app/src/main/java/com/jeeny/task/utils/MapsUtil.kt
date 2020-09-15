/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


class MapsUtil {
    companion object {
        private const val ERROR_DIALOG_REQUEST = 9001
        private val TAG: String = MapsUtil::class.java.simpleName

        fun isServicesOK(context: Activity): Boolean {
            Log.d(TAG, "isServicesOK: checking google services version")
            val available =
                GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context)
            when {
                available == ConnectionResult.SUCCESS -> {
                    Log.d(TAG, "isServicesOK: Google Play Services is working")
                    return true
                }
                GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                    Log.d(TAG, "isServicesOK: an error occurred but we can fix it")
                    val dialog: Dialog = GoogleApiAvailability.getInstance()
                        .getErrorDialog(
                            context, available,
                            ERROR_DIALOG_REQUEST
                        )
                    dialog.show()
                }
                else -> {
                    ToastUtil.showCustomToast(context, "You can't make map requests")
                }
            }
            return false
        }

        fun getMarkerBitmapFromView(context: Activity): Bitmap? {
            val customMarkerView: View? =
                LayoutInflater.from(context).inflate(com.jeeny.task.R.layout.custom_marker, null)
            customMarkerView?.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            customMarkerView?.layout(
                0,
                0,
                customMarkerView.measuredWidth,
                customMarkerView.measuredHeight
            );
            customMarkerView?.buildDrawingCache();
            val returnedBitmap = Bitmap.createBitmap(
                customMarkerView!!.measuredWidth, customMarkerView.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(returnedBitmap)
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
            val drawable = customMarkerView.background

            drawable?.draw(canvas);
            customMarkerView.draw(canvas);
            return returnedBitmap;

        }

    }
}