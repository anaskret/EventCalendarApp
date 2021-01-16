package com.example.eventcalendarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddJsonActivity : AppCompatActivity() {

    private lateinit var submitBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var jsonInput: EditText

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_json)
        val textView: TextView = findViewById(R.id.text_above_info)
        /*[{"name":"test #1","date":"16-01-2021","hour": 12, "minute": 10},
			{"name":"tes #2","date":"16-01-2021","hour": 16, "minute": 12}]*/
        submitBtn = findViewById(R.id.submit_json)
        cancelBtn = findViewById(R.id.cancel_json)

        submitBtn.setOnClickListener {
            jsonInput = findViewById(R.id.json_input)
            val json = jsonInput.text.toString()

            val gson = Gson()
            val arrayTutorialType = object : TypeToken<Array<EventHelperClass>>() {}.type

            var events: Array<EventHelperClass> = gson.fromJson(json, arrayTutorialType)

            database = FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = database.getReference("Events");

            for(item in events){
                if ((item.name.length in 4..49) && (item.hour in 1..24) && (item.minute in 1..60))
                {
                    val key: String = database.getReference("Events").push().key.toString()
                    reference.child(key).setValue(item)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    textView.text = "One or more events were incorrect"
                }
            }
        }

        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}