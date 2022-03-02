package com.chul.plannode.calendar

import android.graphics.Color
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.Calendar

class Schedule private constructor(
    private val startDate: LocalDateTime,
    private val endDate: LocalDateTime,
    private val title: String,
    private val description: String,
    private val backgroundColor: Int
) {

    class Builder {
        private var startDate: LocalDateTime? = null
        private var endDate: LocalDateTime? = null
        private var title = ""
        private var description = ""
        private var backgroundColor = Color.GREEN

        fun setStartDate(date: LocalDateTime): Builder {
            this.startDate = date
            return this
        }

        fun setEndDate(date: LocalDateTime): Builder {
            this.endDate = date
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setBackgroundColor(color: Int): Builder {
            this.backgroundColor = color
            return this
        }

        fun build(): Schedule {
            val sd = startDate ?: LocalDateTime.now()
            val ed = endDate ?: sd.plusHours(1)
            return Schedule(
                sd,
                ed,
                title,
                description,
                backgroundColor
            )
        }
    }
}