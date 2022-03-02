package com.chul.plannode.util

import java.time.DayOfWeek
import java.time.LocalDate

object CalendarUtils {
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    fun firstDayOfWeek(date: LocalDate): LocalDate {
        return when(date.dayOfWeek) {
            DayOfWeek.SUNDAY -> date
            DayOfWeek.MONDAY -> date.minusDays(1)
            DayOfWeek.TUESDAY -> date.minusDays(2)
            DayOfWeek.WEDNESDAY -> date.minusDays(3)
            DayOfWeek.THURSDAY -> date.minusDays(4)
            DayOfWeek.FRIDAY -> date.minusDays(5)
            DayOfWeek.SATURDAY -> date.minusDays(6)
        }
    }

    fun lastDayOfWeek(date: LocalDate): LocalDate {
        return when(date.dayOfWeek) {
            DayOfWeek.SUNDAY -> date.plusDays(6)
            DayOfWeek.MONDAY -> date.plusDays(5)
            DayOfWeek.TUESDAY -> date.plusDays(4)
            DayOfWeek.WEDNESDAY -> date.plusDays(3)
            DayOfWeek.THURSDAY -> date.plusDays(2)
            DayOfWeek.FRIDAY -> date.plusDays(1)
            DayOfWeek.SATURDAY -> date
        }
    }
}