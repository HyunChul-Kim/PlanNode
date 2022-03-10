package com.chul.plannode.calendar

import java.time.LocalDate

interface CalendarListener {
    fun onSelectChange(id: Int, date: LocalDate?)
}