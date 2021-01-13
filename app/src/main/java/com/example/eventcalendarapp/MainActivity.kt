package com.example.eventcalendarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.NonNull

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var myDate: TextView
    private lateinit var addEvent: Button
    private lateinit var seeEvents: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        myDate = findViewById(R.id.myDate)
        addEvent = findViewById(R.id.add_event)
        seeEvents = findViewById(R.id.see_events)

        addEvent.setOnClickListener{
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        seeEvents.setOnClickListener {
            val intent = Intent(this, ListEventsActivity::class.java)
            startActivity(intent)
        }
    }
}