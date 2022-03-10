package com.chul.plannode.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
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
): ViewGroup(context, attrs, defStyle), CalendarListener {

    private var weeksPerMonth = WEEKS_PER_MONTH

    private var mCheckedId = -1
    private var mProtectFromCheckedChange = false
    private val mChildOnCheckedChangeListener = CheckedStateTracker()
    private val mPassThroughListener = PassThroughHierarchyChangeListener()
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    /**
     * the date to parse such as "2007-12-03", not null
     */
    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView) {
            val date = getString(R.styleable.CalendarView_date) ?: ""
            try {
                initCalendar(LocalDate.parse(date), null)
            } catch (e: Exception) {

            }
        }
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener?) {
        mPassThroughListener.onHierarchyChangeListener = listener
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val dayWidth = width.toFloat() / DAYS_PER_WEEK
        val dayHeight = height.toFloat() / weeksPerMonth
        var index = 0
        children.forEach { view ->
            val left = (index % DAYS_PER_WEEK) * dayWidth
            val top = (index / DAYS_PER_WEEK) * dayHeight
            view.layout(left.toInt(), top.toInt(), (left + dayWidth).toInt(), (top + dayHeight).toInt())
            index++
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(mCheckedId != -1) {
            mProtectFromCheckedChange = true
            val date = setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false
            setCheckedId(mCheckedId, date)
        }
    }

    override fun addView(child: View, index: Int, params: LayoutParams?) {
        if(child is DayView) {
            generateId(child)
            if(child.isChecked) {
                mProtectFromCheckedChange = true
                if(mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id, child.getConfiguration().date)
            }
        }
        super.addView(child, index, params)
    }

    fun setOnCheckChangeListener(listener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }

    fun check(id: Int) {
        if(id != -1 && id == mCheckedId) return

        if(mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }

        var date: LocalDate? = null
        if(id != -1) {
            date = setCheckedStateForView(id, true)
        }

        setCheckedId(id, date)
    }

    private fun setCheckedId(id: Int, date: LocalDate?) {
        mCheckedId = id
        mOnCheckedChangeListener?.onCheckedChange(this, mCheckedId, date)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean): LocalDate? {
        val view = findViewById<View>(viewId)
        if(view != null && view is DayView) {
            view.isChecked = checked
            return view.getConfiguration().date
        }
        return null
    }

    private fun generateId(view: DayView) {
        var id = view.id
        if(id == View.NO_ID) {
            id = view.generateId()
            view.id = id
        }
    }

    fun getCheckedViewId() = mCheckedId

    fun clearCheck() {
        check(-1)
    }

    fun initCalendar(localDate: LocalDate, selectedDate: LocalDate?) {
        removeAllViews()
        val firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth())
        val firstDayOfWeek = CalendarUtils.firstDayOfWeek(firstDayOfMonth)
        val lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth())
        val lastDayOfWeek = CalendarUtils.lastDayOfWeek(lastDayOfMonth)
        val today = LocalDate.now()
        val firstDayOfCurrentWeek = CalendarUtils.firstDayOfWeek(today)
        val lastDayOfCurrentWeek = CalendarUtils.lastDayOfWeek(today)
        weeksPerMonth = ChronoUnit.WEEKS.between(firstDayOfWeek, lastDayOfWeek) + 1
        if(firstDayOfWeek.month != localDate.month) {
            val lastDayOfPrevMonth = firstDayOfWeek.with(TemporalAdjusters.lastDayOfMonth())
            for(day in firstDayOfWeek.dayOfMonth..lastDayOfPrevMonth.dayOfMonth) {
                val date = LocalDate.of(lastDayOfPrevMonth.year, lastDayOfPrevMonth.month, day)
                val isCurrentWeek = date >= firstDayOfCurrentWeek && date <= lastDayOfCurrentWeek
                addView(
                    DayView(context).apply {
                        setConfiguration(
                            DayView.Configuration.Builder()
                                .setDate(date)
                                .setCurrentWeek(isCurrentWeek)
                                .setToday(date == today)
                                .build())
                    }
                )
            }
        }
        for(day in firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth) {
            val date = LocalDate.of(localDate.year, localDate.month, day)
            val isCurrentWeek = date >= firstDayOfCurrentWeek && date <= lastDayOfCurrentWeek
            addView(
                DayView(context).apply {
                    setConfiguration(
                        DayView.Configuration.Builder()
                            .setDate(date)
                            .setCurrentMonth(true)
                            .setCurrentWeek(isCurrentWeek)
                            .setSelected(date == selectedDate)
                            .setToday(date == today)
                            .build()
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
                        setConfiguration(
                            DayView.Configuration.Builder()
                                .setDate(date)
                                .setCurrentWeek(isCurrentWeek)
                                .setToday(date == today)
                                .build()
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

    interface OnCheckedChangeListener {
        fun onCheckedChange(view: CalendarView, id: Int, date: LocalDate?)
    }

    private inner class CheckedStateTracker: DayView.CheckedChangeListener {
        override fun onCheckedChange(view: DayView, checked: Boolean) {
            if(mProtectFromCheckedChange) return

            mProtectFromCheckedChange = true
            if(mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false

            val id = view.id
            val date = view.getConfiguration().date
            setCheckedId(id, date)
        }
    }
    private inner class PassThroughHierarchyChangeListener: OnHierarchyChangeListener {
        var onHierarchyChangeListener: OnHierarchyChangeListener? = null
        override fun onChildViewAdded(parent: View, child: View) {
            if(parent is CalendarView && child is DayView) {
                child.setOnCheckedChangeListener(mChildOnCheckedChangeListener)
            }
            onHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View) {
            if(parent is CalendarView && child is DayView) {
                child.setOnCheckedChangeListener(null)
            }
            onHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }

    }

    override fun onSelectChange(id: Int, date: LocalDate?) {
        check(id)
    }
}