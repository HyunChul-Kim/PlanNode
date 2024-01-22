package com.chul.plannode.calendar

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.chul.plannode.R
import com.chul.plannode.util.dpToPx
import kotlin.math.max
import kotlin.math.min


class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {

    private val mDesiredMonthHeight = resources.getDimensionPixelSize(R.dimen.month_height)
    private val mDesiredDayOfWeekHeight = resources.getDimensionPixelSize(R.dimen.day_of_week_height)
    private val mDesiredDayWidth = resources.getDimensionPixelSize(R.dimen.day_width)
    private val mDesiredDayHeight = resources.getDimensionPixelSize(R.dimen.day_width)
    private val mDesiredDaySelectorRadius = resources.getDimensionPixelSize(R.dimen.day_selector_radius)
    private val mMinimumWidth = resources.getDimensionPixelSize(R.dimen.minimum_width)

    private var mPaddedWidth = 0
    private var mPaddedHeight = 0
    private var mMonthHeight = 0
    private var mDayOfWeekHeight = 0
    private var mDayHeight = 0
    private var mDayWidth = 0
    private var mDaySelectorRadius = 0

    private val mMonthPaint = TextPaint()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val preferredWidth = mDesiredDayWidth * DAYS_IN_WEEK +
                paddingStart + paddingEnd
        val preferredHeight = (mDesiredDayHeight * WEEKS_IN_MONTH) +
                mDesiredMonthHeight + mDesiredDayOfWeekHeight +
                paddingTop + paddingBottom
        val resolveWidth = resolveSize(preferredWidth, widthMeasureSpec)
        val resolveHeight = resolveSize(preferredHeight, heightMeasureSpec)
        setMeasuredDimension(resolveWidth, resolveHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if(!changed) return
        val w = right - left
        val h = bottom - top
        val paddedRight = w - paddingEnd
        val paddedBottom = h - paddingBottom
        val paddedWidth = paddedRight - paddingStart
        val paddedHeight = paddedBottom - paddingTop
        if(paddedWidth == mPaddedWidth || paddedHeight == mPaddedHeight) return

        mPaddedWidth = paddedWidth
        mPaddedHeight = paddedHeight
        val measuredPaddedHeight = measuredHeight - paddingTop - paddingBottom
        val scaleHeight = paddedHeight / measuredPaddedHeight.toFloat()
        val monthHeight = (mDesiredMonthHeight * scaleHeight).toInt()
        val dayWidth = paddedWidth / DAYS_IN_WEEK
        mMonthHeight = monthHeight
        mDayOfWeekHeight = (mDesiredDayOfWeekHeight * scaleHeight).toInt()
        mDayHeight = (mDesiredDayHeight * scaleHeight).toInt()
        mDayWidth = dayWidth

        val maxSelectorWidth = dayWidth / 2 + min(paddingStart, paddingEnd)
        val maxSelectorHeight = mDayHeight / 2 + paddingBottom
        mDaySelectorRadius = min(mDesiredDaySelectorRadius,
            min(maxSelectorWidth, maxSelectorHeight))
    }

    override fun onDraw(canvas: Canvas) {
        canvas.translate(paddingStart.toFloat(), paddingTop.toFloat())
    }

    private fun drawMonth() {

    }

    companion object {
        private const val DAYS_IN_WEEK = 7
        private const val WEEKS_IN_MONTH = 6
    }
}