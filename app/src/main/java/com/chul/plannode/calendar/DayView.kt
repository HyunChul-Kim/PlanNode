package com.chul.plannode.calendar

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.TextPaint
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.view.View
import android.widget.Checkable
import androidx.core.content.withStyledAttributes
import com.chul.plannode.R
import java.time.LocalDate

class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): View(context, attrs, defStyle), Checkable {

    private var mChecked = false
    private var mCheckedChangeListener: CheckedChangeListener? = null
    private var mBroadcasting = false

    private var configuration: Configuration = Configuration.Builder().build()
    private val bounds = Rect()

    private val defaultBackgroundColor = Color.TRANSPARENT
    private val defaultDividerColor = Color.GRAY
    private val defaultDividerWidth = 1f

    private val textBoundPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
    }
    private val backgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = defaultBackgroundColor
        style = Paint.Style.FILL
    }
    private val dividerPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }
    private val textPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 40f
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView) {
            val textSize = getDimensionPixelSize(R.styleable.CalendarView_textSize, 40).toFloat()
            val textColor = getColor(R.styleable.CalendarView_textColor, Color.WHITE)
            textPaint.textSize = textSize
            textPaint.color = textColor
        }
        setBackgroundColor(context.getColor(R.color.default_background))
        isClickable = true
    }

    fun setOnCheckedChangeListener(listener: CheckedChangeListener?) {
        mCheckedChangeListener = listener
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPaint.textSize = height * 0.1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val date = configuration.date.dayOfMonth.toString()
        textPaint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawLine(0f, dividerPaint.strokeWidth / 2, width.toFloat(), dividerPaint.strokeWidth / 2, dividerPaint)
        if(configuration.isToday) {
            canvas.drawRoundRect(
                0f,
                dividerPaint.strokeWidth,
                width.toFloat(),
                dividerPaint.strokeWidth + (height * 0.1f) + bounds.height(),
                10f,
                10f,
                textBoundPaint
            )
        }
        canvas.drawRect(0f, dividerPaint.strokeWidth, width.toFloat(), height.toFloat(), backgroundPaint)
        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat() - 2,
            dividerPaint.strokeWidth + (height * 0.05f) + bounds.height().toFloat(),
            textPaint
        )
    }

    fun setConfiguration(configuration: Configuration) {
        this.configuration = configuration
        textPaint.typeface = if(configuration.selected) Typeface.create(Typeface.DEFAULT, Typeface.BOLD) else Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textBoundPaint.color = configuration.dayBoundColor
        dividerPaint.color = if(configuration.isCurrentWeek) configuration.dividerColor else defaultDividerColor
        dividerPaint.strokeWidth = if(configuration.isCurrentWeek) configuration.dividerStokeWidth else defaultDividerWidth
        mChecked = configuration.selected
        updateBackgroundPaint()
    }

    fun getConfiguration() = configuration

    fun generateId(): Int = configuration.date.hashCode()

    private fun updateBackground() {
        updateBackgroundPaint()
        invalidate()
    }

    private fun updateBackgroundPaint() {
        if(mChecked) {
            backgroundPaint.color = configuration.selectedColor
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        } else {
            backgroundPaint.color = defaultBackgroundColor
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
    }

    override fun performClick(): Boolean {
        toggle()

        val handled = super.performClick()
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }

        return handled
    }

    override fun setChecked(checked: Boolean) {
        if(mChecked != checked) {
            mChecked = checked
            updateBackground()

            if(mBroadcasting) return
            mBroadcasting = true
            mCheckedChangeListener?.onCheckedChange(this, mChecked)
            mBroadcasting = false
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        if(!isChecked) {
            isChecked = !mChecked
        }
    }

    interface CheckedChangeListener {
        fun onCheckedChange(view: DayView, checked: Boolean)
    }

    class Configuration private constructor(
        val date: LocalDate,
        val dayColor: Int,
        val dividerColor: Int,
        val dividerStokeWidth: Float,
        val dayBoundColor: Int,
        val selectedColor: Int,
        val selected: Boolean,
        val isToday: Boolean,
        val isCurrentWeek: Boolean
    ) {

        class Builder {
            private var date: LocalDate? = null
            private var isCurrentMonth: Boolean = false
            private var isCurrentWeek: Boolean = false
            private var isSelected: Boolean = false
            private var isToday: Boolean = false
            private var dayBoundColor: Int = Color.GRAY
            private var selectedColor: Int = Color.parseColor("#7700b52b")
            private var dividerColor: Int = Color.GREEN
            private var dividerWidth: Float = 5f

            fun setDate(date: LocalDate) = apply {
                this.date = date
            }

            fun setCurrentMonth(isCurrentMonth: Boolean) = apply {
                this.isCurrentMonth = isCurrentMonth
            }

            fun setCurrentWeek(isCurrentWeek: Boolean) = apply {
                this.isCurrentWeek = isCurrentWeek
            }

            fun setSelected(isSelected: Boolean) = apply {
                this.isSelected = isSelected
            }

            fun setToday(isToday: Boolean) = apply {
                this.isToday = isToday
            }

            fun setDayBoundColor(color: Int) = apply {
                this.dayBoundColor = color
            }

            fun setSelectedColor(color: Int) = apply {
                this.selectedColor = color
            }

            fun build(): Configuration {
                return Configuration(
                    date ?: LocalDate.now(),
                    Color.WHITE,
                    dividerColor,
                    dividerWidth,
                    dayBoundColor,
                    selectedColor,
                    isSelected,
                    isToday,
                    isCurrentWeek
                )
            }
        }
    }
}