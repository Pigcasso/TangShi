package com.danliren.apps.tangshi.util

import android.content.Context

object SizeUtil {
    fun dpToPx(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale
    }
}