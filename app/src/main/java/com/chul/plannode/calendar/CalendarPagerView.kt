package com.chul.plannode.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.chul.plannode.R
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
    private val format = context.getString(R.string.year_month_title)

    init {
        checkContext(context)
        adapter = CalendarPagerAdapter(context as FragmentActivity)
        binding.calendarPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val diff = position - CalendarPagerAdapter.START_POSITION
                val date = adapter.currentDate.plusMonths(diff.toLong())
                binding.calendarYearMonthTitle.text = String.format(format, date.year, date.month.value)
            }
        })
        binding.calendarPager.adapter = adapter
        binding.calendarPager.setCurrentItem(CalendarPagerAdapter.START_POSITION, false)
    }

    private fun checkContext(context: Context) {
        if(context !is FragmentActivity) throw IllegalArgumentException("Context is not FragmentActivity")
    }
}