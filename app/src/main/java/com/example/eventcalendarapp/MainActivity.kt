package com.example.eventcalendarapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var myDate: TextView
    private lateinit var addEvent: Button
    private lateinit var seeEvents: Button
    private lateinit var jsonActivity: Button

   /* private lateinit var checkEvents: Query
    private lateinit var listOfDates: ArrayList<String?>*/

    private var selectedDate by Delegates.notNull<String>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        myDate = findViewById(R.id.myDate)
        addEvent = findViewById(R.id.add_event)
        seeEvents = findViewById(R.id.see_events)
        jsonActivity = findViewById(R.id.go_to_json_activity)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        selectedDate = "15-01-2021"
        /*val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val dbReference = FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events")

        checkEvents = dbReference

        checkEvents.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()) {
                        for (e in p0.children) {
                            val date = e.getValue(EventHelperClass::class.java)?.getDate()
                            val convertedDate = LocalDate.parse(date, formatter)

                        }
                    }
                }
            })*/

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val c: Calendar = Calendar.getInstance()
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            selectedDate = dateFormat.format(c.time)
            myDate.text = selectedDate
        }

        addEvent.setOnClickListener{
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        seeEvents.setOnClickListener {
            val intent = Intent(this, ListEventsActivity::class.java)
            intent.putExtra("SELECTED_DATE", selectedDate)
            startActivity(intent)
        }

        jsonActivity.setOnClickListener {
            val intent = Intent(this, AddJsonActivity::class.java)
            startActivity(intent)
        }
    }
}