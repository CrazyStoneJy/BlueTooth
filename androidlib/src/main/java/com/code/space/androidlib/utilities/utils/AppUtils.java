package com.code.space.androidlib.utilities.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.code.space.androidlib.app.CSApplication;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public class AppUtils {

    public static boolean debugging;

    public static boolean checkAPI(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    public static String getPkgName(Context context) {
        return ContextUtils.getApplication(context).getPackageName();
    }

    /**
     * debugging or released
     */
    public static boolean debugging() {
        return debugging;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = ContextUtils.getApplication(context).getPackageManager().getPackageInfo(getPkgName(context), PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.ex(e);
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = ContextUtils.getApplication(context).getPackageManager().getPackageInfo(getPkgName(context), PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.ex(e);
            return null;
        }
    }
}
