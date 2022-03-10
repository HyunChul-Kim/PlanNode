package com.chul.plannode.calendar

import java.time.LocalDate

class CalendarController: CalendarView.OnCheckedChangeListener {

    private val calendarListener = HashSet<CalendarListener>()
    var selectedDate: LocalDate = LocalDate.now()

    override fun onCheckedChange(view: CalendarView, id: Int, date: LocalDate?) {
        selectedDate = date ?: return
        for(listener in calendarListener) {
            listener.onSelectChange(id, date)
        }
    }

    fun addCalendarListener(listener: CalendarListener) {
        calendarListener.add(listener)
    }

    fun removeCalendarListener(listener: CalendarListener) {
        calendarListener.remove(listener)
    }

    fun destroy() {
        calendarListener.clear()
    }
}