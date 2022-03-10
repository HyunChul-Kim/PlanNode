package com.chul.plannode.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.time.LocalDate

class CalendarPagerAdapter: FragmentStateAdapter {

    constructor(fragment: Fragment) : super(fragment)
    constructor(activity: FragmentActivity) : super(activity)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle): super(fragmentManager, lifecycle)

    val currentDate = LocalDate.now()
    lateinit var calendarController: CalendarController

    override fun getItemCount() = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val diff = position - START_POSITION
        return CalendarFragment(currentDate.plusMonths(diff.toLong()), calendarController)
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }
}