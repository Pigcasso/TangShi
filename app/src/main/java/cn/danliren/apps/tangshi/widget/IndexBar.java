package cn.danliren.apps.tangshi.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.Nullable;

import cn.danliren.apps.tangshi.R;

/**
 * 用在地址、通讯录检索等一些带有ListView的界面。可以滑动或点击{@link IndexBar},
 * 注册监听{@link IIndexBarFilter},来完成你需要的功能。
 *
 * @author bri (http://my.oschina.net/droideep)
 * @since 15-5-9.
 */
public class IndexBar extends View {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_PRESSED = 1;
    private static final String LOG_TAG = "IndexBar";
    private static final int INVALID_SECTION_INDEX = -1;
    /**
     * {@link Paint}, which is used for drawing the elements of
     * <b>IndexBar</b>
     */
    protected final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mState = STATE_NORMAL;
    /**
     * 设置索引条正常显示的背景颜色,
     */
    private int mIndexBarColorNormal = Color.TRANSPARENT;
    /**
     * 设置索引条按压时的背景色
     */
    private int mIndexBarColorPressed = Color.GRAY;
    /**
     * 设置字体的颜色
     */
    private int mAlphabetTextColor = Color.BLACK;
    /**
     * 设置字体的大小
     */
    private float mAlphabetTextSize = dpToPx(getContext(), 10);
    /**
     * 设置索引条中字母的间距
     * <p>
     * 这个属性只有在 android:layout_height="wrap_content" 时有效。
     * 当 android:layout_height="match_parent" 时,需要通过字母的高度、{@link IndexBar#getMeasuredHeight()},
     * 来计算字母的间距，具体看{@link IndexBar#onMeasure(int, int)}的实现
     * </p>
     */
    private float mAlphabetPadding = dpToPx(getContext(), 5);
    /**
     * 设置索引条左右两侧与字母的间距，默认等于{@link #mAlphabetPadding}
     */
    private float mIndexBarSides = mAlphabetPadding;

    /**
     * 设置索引条圆角,默认为直角矩形
     */
    private float mIndexBarRound = dpToPx(getContext(), 0);
    //默认在IndexBar以外，仍然可以滑动
    private boolean mWithinIndexBar = false;
    private String[] mSections = new String[0];
    private IIndexBarFilter mIndexBarFilter;
    private boolean mIsIndexing;

    private int mCurrentSectionPosition;


    public IndexBar(Context context) {
        super(context);
        initIndexBar();
    }

    public IndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initIndexBar(context, attrs, 0, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initIndexBar(context, attrs, defStyleAttr, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(calculateMeasuredWidth(), calculateMeasureHeight());

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        LayoutParams lp = getLayoutParams();
        // 获取IndexBar顶部和底部的margin，这里不用关心左右两边的margin
        int margin = 0;
        // 如果布局文件使用了margin属性时，需要根据margin、mAlphabetTextSize、
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
            margin = mlp.topMargin + mlp.bottomMargin;
        }

        /**
         * 判断IndexBar高度的裁剪模式,如果{@link heightMode} 为{@link MeasureSpec#EXACTLY}，
         * 表示用户设置了一个确切的高度值，或者使用了{@link LayoutParams#MATCH_PARENT}属性。
         * 如果{@link heightMode} 为 {@link MeasureSpec#AT_MOST},
         * 表示用户使用了{@link LayoutParams#WRAP_CONTENT}属性，这时，我们会使用默认的{@link #mAlphabetPadding}，
         * 或者用户设置的值
         */
        if (heightMode == MeasureSpec.EXACTLY) { // 计算mAlphabetPadding
            final int length = mSections.length;
            mAlphabetPadding = (heightSize - length * mAlphabetTextSize - margin) / (length + 1);

            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(calculateMeasuredWidth(), calculateMeasureHeight());
        }


    }

    private int calculateMeasuredWidth() {
        final int measuredWidth = (int) (getAlphabetTextSize() + getIndexBarSides() * 2);
        Log.v(LOG_TAG, "Calculated measured width = " + measuredWidth);
        return measuredWidth;
    }

    private int calculateMeasureHeight() {
        final int length = mSections.length;
        final float measureHeight = length * getAlphabetTextSize() + (length + 1) * getAlphabetPadding();
        return (int) measureHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(LOG_TAG, "Index Bar onDraw called");
        drawRect(canvas);
        drawSections(canvas);
    }

    @SuppressWarnings("all")
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (contains(event.getX(), event.getY())) {
                    mIsIndexing = true;
                    filterListItem(event.getY());
                    setState(STATE_PRESSED);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsIndexing) {
                    if (mWithinIndexBar) { //只能在IndexBar内部滑动
                        if (contains(event.getX(), event.getY())) {
                            filterListItem(event.getY());
                        } else { // 否则，如果滑动的位置已经不在IndexBar内部时
                            mIsIndexing = false;
                            mCurrentSectionPosition = INVALID_SECTION_INDEX;
                            setState(STATE_NORMAL);
                            if (mIndexBarFilter != null) {
                                mIndexBarFilter.filterList(0, mCurrentSectionPosition, null);
                            }
                            return true;
                        }
                    } else { // 可以在IndexBar以外滑动
                        filterListItem(event.getY());
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsIndexing) {
                    mIsIndexing = false;
                    mCurrentSectionPosition = INVALID_SECTION_INDEX;
                    setState(STATE_NORMAL);
                    if (mIndexBarFilter != null) {
                        mIndexBarFilter.filterList(0, mCurrentSectionPosition, null);
                    }
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    private boolean contains(float y) {
        return (y >= 0 && y <= getHeight());
    }

    private boolean contains(float x, float y) {
//        return (x >= getLeft() && y >= getTop() && (y <= getTop() + getMeasuredHeight()));
        return (x >= 0 && y >= 0 && x <= getWidth() && y <= getHeight());
    }

    /**
     * 根据当前滑动的Y轴坐标，给{@link #mCurrentSectionPosition}赋值
     *
     * @param sideIndexY
     */
    private void filterListItem(float sideIndexY) {
        mCurrentSectionPosition = (int) (((sideIndexY) - mAlphabetPadding) /
                ((getMeasuredHeight() - (2 * mAlphabetPadding)) / mSections.length));
        if (mCurrentSectionPosition >= 0 && mCurrentSectionPosition < mSections.length) {
            Log.d(LOG_TAG, "CurrentSectionPosition:" + mCurrentSectionPosition);
            String previewText = mSections[mCurrentSectionPosition];
            if (mIndexBarFilter != null) {
                mIndexBarFilter.filterList(sideIndexY, mCurrentSectionPosition, previewText);
            }
        }
    }

    private void initIndexBar() {
        initLayerType();
        Log.v(LOG_TAG, "Index Bar initialized");
    }

    private void initIndexBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        initLayerType();
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, defStyleRes);
        try {

            initIndexBarColor(attributes);
            initIndexBarColorPressed(attributes);
            initAlphabetTextColor(attributes);
            initAlphabetTextSize(attributes);
            initAlphabetPadding(attributes);
            initIndexBarSides(attributes);
            initIndexBarRound(attributes);
            initWithinIndexBar(attributes);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Unable to read attr", e);
        } finally {
            attributes.recycle();
        }
    }

    private void initWithinIndexBar(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_withinIndexBar)) {
            mWithinIndexBar = attributes.getBoolean(R.styleable.IndexBar_withinIndexBar, mWithinIndexBar);
            Log.v(LOG_TAG, "Initialized Within Index Bar: " + getWithinIndexBar());
        }
    }

    private void initAlphabetPadding(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_alphabetPadding)) {
            mAlphabetPadding = attributes.getDimension(R.styleable.IndexBar_alphabetPadding, mAlphabetPadding);
            Log.v(LOG_TAG, "Initialized Alphabet Offset: " + getAlphabetPadding());
        }
    }

    private void initIndexBarSides(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_indexBarSides)) {
            mIndexBarSides = attributes.getDimension(R.styleable.IndexBar_indexBarSides, mIndexBarSides);
            Log.v(LOG_TAG, "Initialized Alphabet Offset: " + getIndexBarSides());
        }
    }

    private void initAlphabetTextSize(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_alphabetTextSize)) {
            mAlphabetTextSize = attributes.getDimension(R.styleable.IndexBar_alphabetTextSize, mAlphabetTextSize);
            Log.v(LOG_TAG, "Initialized Alphabet TextSize: " + getAlphabetTextSize());
        }
    }

    private void initAlphabetTextColor(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_alphabetTextColor)) {
            mAlphabetTextColor = attributes.getColor(R.styleable.IndexBar_alphabetTextColor, mAlphabetTextColor);
            Log.v(LOG_TAG, "Initialized Alphabet TextColor: " + getAlphabetTextColor());
        }
    }

    private void initIndexBarColorPressed(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_indexBarColorPressed)) {
            mIndexBarColorPressed = attributes.getColor(R.styleable.IndexBar_indexBarColorPressed, mIndexBarColorPressed);
            Log.v(LOG_TAG, "Initialized Index Bar Color Pressed: " + getIndexBarColorPressed());
        }
    }

    private void initIndexBarColor(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_indexBarColorNormal)) {
            mIndexBarColorNormal = attributes.getColor(R.styleable.IndexBar_indexBarColorNormal, mIndexBarColorNormal);
            Log.v(LOG_TAG, "Initialized Index Bar Color: " + getIndexBarColor());
        }
    }

    private void initIndexBarRound(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.IndexBar_indexBarRound)) {
            mIndexBarRound = attributes.getDimension(R.styleable.IndexBar_indexBarRound, mIndexBarRound);
            Log.v(LOG_TAG, "Initialized Index Bar Color: " + getIndexBarRound());
        }
    }

    /**
     * Initializes the layer type needed for shadows drawing
     * <p/>
     * Might be called if target API is HONEYCOMB (11) and higher
     */
    private void initLayerType() {
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        Log.v(LOG_TAG, "Layer type initialized");
    }

    private void drawRect(Canvas canvas) {
        resetPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getState() == STATE_NORMAL ? getIndexBarColor() : getIndexBarColorPressed());
        float l = 0;
        float t = 0;
        float r = l + getWidth();
        float b = t + getHeight();
        RectF rectF = new RectF(l, t, r, b);
        if (hasRound()) {
            canvas.drawRoundRect(rectF, mIndexBarRound, mIndexBarRound, paint);
        } else {
            canvas.drawRect(rectF, paint);
        }
    }

    private void drawSections(Canvas canvas) {
        if (mSections == null || mSections.length == 0)
            return;
        resetPaint();

        final int length = mSections.length;

        // 字母的宽度
        final float width = getWidth() - mIndexBarSides * 2;
        final float height = (getHeight() - ((length + 1) * mAlphabetPadding)) / length;
        for (int i = 0; i < length; i++) {
            final float l = mIndexBarSides;
            final float t = (i + 1) * mAlphabetPadding + i * height;
            final float r = l + width;
            final float b = t + height;

            RectF targetRect = new RectF(l, t, r, b);
            paint.setColor(mAlphabetTextColor);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(mAlphabetTextSize);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            float baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

            canvas.drawText(mSections[i], targetRect.centerX(), baseline, paint);
        }
    }

    private boolean hasRound() {
        return getIndexBarRound() > 0.0f;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        if (mState != state) {
            mState = state;
            invalidate();
        }
    }

    public int getIndexBarColor() {
        return mIndexBarColorNormal;
    }

    public void setIndexBarColorNormal(int mIndexBarColorNormal) {
        if (this.mIndexBarColorNormal != mIndexBarColorNormal) {
            this.mIndexBarColorNormal = mIndexBarColorNormal;
            invalidate();
        }
    }

    public int getIndexBarColorPressed() {
        return mIndexBarColorPressed;
    }

    public void setIndexBarColorPressed(int mIndexBarColorPressed) {
        if (this.mIndexBarColorPressed != mIndexBarColorPressed) {
            this.mIndexBarColorPressed = mIndexBarColorPressed;
            invalidate();
        }
    }

    public int getAlphabetTextColor() {
        return mAlphabetTextColor;
    }

    public void setAlphabetTextColor(int mAlphabetTextColor) {
        if (this.mAlphabetTextColor != mAlphabetTextColor) {
            this.mAlphabetTextColor = mAlphabetTextColor;
            invalidate();
        }
    }

    public float getAlphabetTextSize() {
        return mAlphabetTextSize;
    }

    public void setAlphabetTextSize(float alphabetTextSize) {
        if (mAlphabetTextSize != alphabetTextSize) {
            mAlphabetTextSize = alphabetTextSize;
            invalidate();
        }
    }

    public float getAlphabetPadding() {
        return mAlphabetPadding;
    }

    public void setAlphabetPadding(float mAlphabetPadding) {
        if (this.mAlphabetPadding != mAlphabetPadding) {
            this.mAlphabetPadding = mAlphabetPadding;
            invalidate();
        }
    }

    public float getIndexBarSides() {
        return mIndexBarSides;
    }

    public float getIndexBarRound() {
        return mIndexBarRound;
    }

    public void setIndexBarRound(float mIndexBarRound) {
        if (this.mIndexBarRound != mIndexBarRound) {
            this.mIndexBarRound = mIndexBarRound;
            invalidate();
        }
    }

    public boolean getWithinIndexBar() {
        return mWithinIndexBar;
    }

    public void setWithinIndexBar(boolean mWithinIndexBar) {
        if (this.mWithinIndexBar != mWithinIndexBar) {
            this.mWithinIndexBar = mWithinIndexBar;
            invalidate();
        }
    }

    public String[] getSections() {
        return mSections;
    }

    public void setSections(String[] mSections) {
        this.mSections = mSections;
        requestLayout();
        invalidate();
    }

    /**
     * Resets the paint to its default values and sets initial flags to it
     * Use this method before drawing the new element of the view
     */
    protected final void resetPaint() {
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Log.v(LOG_TAG, "Paint reset");
    }

    public void setIndexBarFilter(IIndexBarFilter indexBarFilter) {
        this.mIndexBarFilter = indexBarFilter;
    }

    public interface IIndexBarFilter {
        /**
         * @param sideIndexY  滑动IndexBar的Y轴坐标
         * @param position    字母的索引位置
         * @param previewText 手指触摸的字母
         */
        void filterList(float sideIndexY, int position, @Nullable String previewText);
    }

    private static float dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }
}