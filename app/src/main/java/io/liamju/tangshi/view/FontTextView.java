package io.liamju.tangshi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import io.liamju.tangshi.TangApplication;
import io.liamju.tangshi.utils.TypefaceUtil;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/15
 */
public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        Typeface typeface = TangApplication.getInstance().getFontTypeface();
        setTypeface(typeface);
        super.setText(text, type);
    }
}
