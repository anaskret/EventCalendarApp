package com.example.eventcalendarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.*
import kotlin.properties.Delegates

class ListEventsActivity : AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var eventList: ListView
    private lateinit var dbReference: DatabaseReference
    private lateinit var checkEvents: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        eventList = findViewById(R.id.event_list)
        backBtn = findViewById(R.id.exit_list_of_events)

        dbReference = FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events")

        val date = intent.getStringExtra("SELECTED_DATE")
        //val date = "13-01-2021"

        checkEvents = dbReference

        checkEvents.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val listOfEvents = ArrayList<String?>()
                    //val listOfEventNames = ArrayList<String>()
                    for (e in p0.children) {
                        val event = e.getValue(EventHelperClass::class.java)?.ToString()

                        //val eventName = e.child("name").value.toString()
                        if (e.getValue(EventHelperClass::class.java)?.getDate() == date) {
                            listOfEvents.add(event)
                        }
                        // listOfEventNames.add((eventName))
                    }
                    val arrayAdapter = ArrayAdapter(this@ListEventsActivity, android.R.layout.simple_list_item_1, listOfEvents)
                    eventList.adapter = arrayAdapter
                }
            }

        })

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}