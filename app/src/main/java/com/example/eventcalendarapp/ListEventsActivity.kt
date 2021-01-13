package com.example.eventcalendarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class ListEventsActivity : AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var eventList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        eventList = findViewById(R.id.event_list)
        backBtn = findViewById(R.id.exit_list_of_events)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}