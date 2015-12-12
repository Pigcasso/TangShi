package io.liamju.tangshi.utils;

import android.os.Build;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/9
 */
public class VersionUtil {

    /**
     * 设备SDK版本是否大于11
     *
     * @return SDK版本大于11返回true，否则返回false
     */
    public static boolean hasHoneycomb11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
}
