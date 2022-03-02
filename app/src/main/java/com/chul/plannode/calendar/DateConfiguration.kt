package com.chul.plannode.calendar

import android.graphics.Color
import androidx.core.graphics.alpha

class DateConfiguration(
    private val dayOfMonth: Int,
    private val isCurrentMonth: Boolean,
    private val isCurrentWeek: Boolean,
    private val isToday: Boolean
) {
    fun getDate(): Int {
        return dayOfMonth
    }

    fun getTextColor(): Int {
        return if(isCurrentMonth) {
            Color.parseColor("#FFFFFF")
        } else {
            Color.parseColor("#77000000")
        }
    }

    fun isCurrentWeek() = isCurrentWeek

    fun isToday(): Boolean = isToday
}