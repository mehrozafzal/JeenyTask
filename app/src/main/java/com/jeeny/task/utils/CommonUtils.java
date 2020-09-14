/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.provider.Settings;

import java.io.IOException;
import java.io.InputStream;

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open(jsonFileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, "UTF-8");
    }

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}