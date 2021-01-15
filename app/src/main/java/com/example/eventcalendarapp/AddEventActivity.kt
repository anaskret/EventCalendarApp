package com.example.eventcalendarapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class AddEventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private lateinit var cancelBtn: Button
    private lateinit var submitBtn: Button
    private lateinit var nameInput: EditText
    private lateinit var timeInputText: TextView
    private lateinit var dateInputText: TextView

    private lateinit var timePickerBtn: Button
    private lateinit var datePickerBtn: Button

    private var timePickerHour by Delegates.notNull<Int>()
    private var timePickerMinute by Delegates.notNull<Int>()
    private var date by Delegates.notNull<String>()

    private lateinit var timePicker: DialogFragment
    private lateinit var datePicker: DialogFragment

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var helperClass: EventHelperClass

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        cancelBtn = findViewById(R.id.cancel_creating_new_event)
        submitBtn = findViewById((R.id.submit_new_event))
        timePickerBtn = findViewById(R.id.time_picker_btn)
        datePickerBtn = findViewById(R.id.date_picker_btn)

        cancelBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        submitBtn.setOnClickListener{
            nameInput = findViewById(R.id.event_name_input)

            val name = nameInput.text.toString()

            database = FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = database.getReference("Events");

            helperClass = EventHelperClass(name, date, timePickerHour, timePickerMinute)

            val key = database.getReference("Events").push().key.toString()

            reference.child(key).setValue(helperClass)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        timePickerBtn.setOnClickListener {
           timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }

        datePickerBtn.setOnClickListener {
            datePicker = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }
    }
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        timeInputText = findViewById(R.id.event_timeTextView_input)

        timePickerHour = hourOfDay
        timePickerMinute = minute

        timeInputText.text = "Hour: $hourOfDay Minute: $minute"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        date = dateFormat.format(c.time)

        dateInputText = findViewById(R.id.event_dateTextView_input)

        dateInputText.text = date
    }
}