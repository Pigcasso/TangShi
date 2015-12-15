package io.liamju.tangshi.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/15
 */
public class TypefaceUtil {

    public static final String ICON_FONT_PATH = "iconfont/iconfont.ttf";
    public static final String FONT_PATH = "fonts/ziti.ttf";

    public static Typeface getFontTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FONT_PATH);
    }

    public static Typeface getIconTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), ICON_FONT_PATH);
    }
}
