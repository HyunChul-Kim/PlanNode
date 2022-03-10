package com.chul.plannode.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chul.plannode.databinding.FragmentCalendarBinding
import java.time.LocalDate

class CalendarFragment(
    private val date: LocalDate,
    private val calendarController: CalendarController
): Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentCalendarView.initCalendar(date, calendarController.selectedDate)
        binding.fragmentCalendarView.setOnCheckChangeListener(calendarController)
        calendarController.addCalendarListener(binding.fragmentCalendarView)
    }

    override fun onStart() {
        super.onStart()
        Log.i("#chul", "$TAG $date onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("#chul", "$TAG $date onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("#chul", "$TAG $date onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("#chul", "$TAG $date onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        calendarController.removeCalendarListener(binding.fragmentCalendarView)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("#chul", "$TAG $date onDestroy()")
    }

    companion object {
        private const val TAG = "[CalendarFragment]"
    }
}