package io.liamju.tangshi;

import android.app.Application;
import android.graphics.Typeface;

import im.fir.sdk.FIR;
import io.liamju.tangshi.utils.TypefaceUtil;


/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/29
 */
public class TangApplication extends Application {

    private static TangApplication mInstance;

    public static TangApplication getInstance() {
        return mInstance;
    }

    private Typeface iconTypeface;
    private Typeface fontTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        mInstance = this;
        iconTypeface = TypefaceUtil.getIconTypeface(this);
        fontTypeface = TypefaceUtil.getFontTypeface(this);
    }

    public Typeface getIconTypeface() {
        return iconTypeface;
    }

    public Typeface getFontTypeface() {
        return fontTypeface;
    }


}
