package cn.danliren.apps.tangshi.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.util.SizeUtil

class ItemView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        orientation = HORIZONTAL
        minimumHeight = SizeUtil.dpToPx(context, 56F).toInt()
        gravity = Gravity.CENTER_VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0)
        val heading = a.getDrawable(R.styleable.ItemView_heading)
        val tailing = a.getDrawable(R.styleable.ItemView_tailing)
        val title = a.getString(R.styleable.ItemView_title)
        val subtitle = a.getString(R.styleable.ItemView_subtitle)
        a.recycle()
        // 标题
        val titleView = TextView(context)
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.text = title
        titleView.setTextColor(ContextCompat.getColor(context, R.color.primary_text))
        titleView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.font_larger)
        )
        titleView.compoundDrawablePadding = SizeUtil.dpToPx(context, 16F).toInt()
        titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(heading, null, null, null)
        addView(
            titleView,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                leftMargin = SizeUtil.dpToPx(context, 16F).toInt()
                weight = 1F
            }
        )
        // 副标题
        val subtitleView = TextView(context)
        subtitleView.gravity = Gravity.CENTER_VERTICAL
        subtitleView.text = subtitle
        subtitleView.setTextColor(ContextCompat.getColor(context, R.color.secondary_text))
        subtitleView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.font_normal)
        )
        subtitleView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, tailing, null)
        addView(
            subtitleView,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                rightMargin = SizeUtil.dpToPx(context, 16F).toInt()
            }
        )
    }
}