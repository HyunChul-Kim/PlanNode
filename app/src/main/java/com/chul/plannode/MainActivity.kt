package com.chul.plannode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chul.plannode.databinding.ActivityMainBinding
import com.chul.plannode.util.CalendarUtils
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}