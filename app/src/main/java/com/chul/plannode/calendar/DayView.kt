package com.chul.plannode.calendar

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.chul.plannode.R

class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): View(context, attrs, defStyle), View.OnClickListener {

    private var configuration: DateConfiguration? = null
    private val bounds = Rect()

    private val selectedBackgroundColor = context.getColor(R.color.selected_background)
    private val defaultBackgroundColor = Color.TRANSPARENT

    private val todayPaint = Paint(ANTI_ALIAS_FLAG).apply {
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
        setOnClickListener(this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPaint.textSize = height * 0.1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val date = configuration?.getDate().toString()
        textPaint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawLine(0f, dividerPaint.strokeWidth / 2, width.toFloat(), dividerPaint.strokeWidth / 2, dividerPaint)
        canvas.drawRoundRect(0f, dividerPaint.strokeWidth, width.toFloat(), dividerPaint.strokeWidth + (height * 0.1f) + bounds.height(), 10f, 10f, todayPaint)
        canvas.drawRect(0f, dividerPaint.strokeWidth, width.toFloat(), height.toFloat(), backgroundPaint)
        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat() - 2,
            dividerPaint.strokeWidth + (height * 0.05f) + bounds.height().toFloat(),
            textPaint
        )
    }

    fun setConfiguration(configuration: DateConfiguration) {
        this.configuration = configuration
        textPaint.typeface = if(configuration.isToday()) Typeface.create(Typeface.DEFAULT, Typeface.BOLD) else Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        todayPaint.color = if(configuration.isToday()) Color.GRAY else Color.TRANSPARENT
        dividerPaint.color = if(configuration.isCurrentWeek()) Color.GREEN else Color.GRAY
        dividerPaint.strokeWidth = if(configuration.isCurrentWeek()) 5f else 1f
    }

    override fun onClick(v: View?) {
        if(!isSelected) {
            isSelected = !isSelected
        }
        if(isSelected) {
            backgroundPaint.color = selectedBackgroundColor
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        } else {
            backgroundPaint.color = defaultBackgroundColor
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
        invalidate()
    }
}