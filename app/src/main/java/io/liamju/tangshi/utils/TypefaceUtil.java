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
    /**
     * 来自造字公房的字体：http://www.makefont.com/font.html?MFKeSong_Noncommercial_Regular###
     */
    public static final String FONT_PATH = "fonts/Regular.otf";

    public static Typeface getFontTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FONT_PATH);
    }

    public static Typeface getIconTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), ICON_FONT_PATH);
    }
}
