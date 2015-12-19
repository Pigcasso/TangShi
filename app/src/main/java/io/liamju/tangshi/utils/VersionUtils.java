package io.liamju.tangshi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/16
 */
public class VersionUtils {

    public static String getVersionName(Context applicationContext) {
        String version = null;
        try {
            PackageManager pm = applicationContext.getPackageManager();
            PackageInfo pi;
            pi = pm.getPackageInfo(applicationContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return version;
        }
    }
}
