package com.chul.plannode.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.chul.plannode.databinding.ViewCalendarPagerBinding

class CalendarPagerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewCalendarPagerBinding.inflate(
        LayoutInflater.from(context),
        this,
        true)
    private val adapter: CalendarPagerAdapter

    init {
        checkContext(context)
        adapter = CalendarPagerAdapter(context as FragmentActivity)
        binding.calendarPager.adapter = adapter
        binding.calendarPager.currentItem = CalendarPagerAdapter.START_POSITION
    }

    private fun checkContext(context: Context) {
        if(context !is FragmentActivity) throw IllegalArgumentException("Context is not FragmentActivity")
    }
}