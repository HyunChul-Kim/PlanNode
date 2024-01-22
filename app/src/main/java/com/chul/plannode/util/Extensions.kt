package com.chul.plannode.util

import android.content.Context

fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}