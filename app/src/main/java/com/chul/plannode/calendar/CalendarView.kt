package com.chul.plannode.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.chul.plannode.R
import com.chul.plannode.util.CalendarUtils
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ViewGroup(context, attrs, defStyle) {

    private var weeksPerMonth = WEEKS_PER_MONTH

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val dayWidth = (width / DAYS_PER_WEEK).toFloat()
        val dayHeight = (height / weeksPerMonth).toFloat()
        var index = 0
        children.forEach { view ->
            val left = (index % DAYS_PER_WEEK) * dayWidth
            val top = (index / DAYS_PER_WEEK) * dayHeight
            view.layout(left.toInt(), top.toInt(), (left + dayWidth).toInt(), (top + dayHeight).toInt())
            index++
        }
    }

    fun initCalendar(localDate: LocalDate) {
        val firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth())
        val firstDayOfWeek = CalendarUtils.firstDayOfWeek(firstDayOfMonth)
        val lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth())
        val lastDayOfWeek = CalendarUtils.lastDayOfWeek(lastDayOfMonth)
        val firstDayOfCurrentWeek = CalendarUtils.firstDayOfWeek(localDate)
        val lastDayOfCurrentWeek = CalendarUtils.lastDayOfWeek(localDate)
        weeksPerMonth = ChronoUnit.WEEKS.between(firstDayOfWeek, lastDayOfWeek) + 1
        if(firstDayOfWeek.month != localDate.month) {
            val lastDayOfPrevMonth = firstDayOfWeek.with(TemporalAdjusters.lastDayOfMonth())
            for(day in firstDayOfWeek.dayOfMonth..lastDayOfPrevMonth.dayOfMonth) {
                val date = LocalDate.of(lastDayOfPrevMonth.year, lastDayOfPrevMonth.month, day)
                val isCurrentWeek = date >= firstDayOfCurrentWeek && date <= lastDayOfCurrentWeek
                addView(
                    DayView(context).apply {
                        setConfiguration(DateConfiguration(
                            day,
                            false,
                            isCurrentWeek,
                            false)
                        )
                    }
                )
            }
        }
        for(day in firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth) {
            val date = LocalDate.of(localDate.year, localDate.month, day)
            val isCurrentWeek = date >= firstDayOfCurrentWeek && date <= lastDayOfCurrentWeek
            addView(
                DayView(context).apply {
                    setConfiguration(DateConfiguration(
                        day,
                        true,
                        isCurrentWeek,
                        date == LocalDate.now())
                    )
                }
            )
        }
        if(lastDayOfMonth.month != lastDayOfWeek.month) {
            val firstDayOfNextMonth = lastDayOfWeek.with(TemporalAdjusters.firstDayOfMonth())
            for(day in firstDayOfNextMonth.dayOfMonth..lastDayOfWeek.dayOfMonth) {
                val date = LocalDate.of(firstDayOfNextMonth.year, firstDayOfNextMonth.month, day)
                val isCurrentWeek = date >= firstDayOfCurrentWeek && date <= lastDayOfCurrentWeek
                addView(
                    DayView(context).apply {
                        setConfiguration(DateConfiguration(day,
                            false,
                            isCurrentWeek,
                            false)
                        )
                    }
                )
            }
        }
    }

    companion object {
        const val DAYS_PER_WEEK = 7
        const val WEEKS_PER_MONTH: Long = 5
    }
}