package io.liamju.tangshi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import io.liamju.tangshi.AppConstants;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/7
 */
public class IconTextView extends TextView {
    public IconTextView(Context context) {
        super(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), AppConstants.ICON_FONT_PATH);
        setTypeface(typeface);
        super.setText(text, type);
    }
}
