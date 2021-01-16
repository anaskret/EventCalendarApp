package com.example.eventcalendarapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File
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

    /*private lateinit var openDialogBtn: Button
    private lateinit var upBtn: Button
    private lateinit var textFolder: TextView
    private lateinit var dialogListView: ListView

    private lateinit var root: File
    private lateinit var curFolder: File
    private lateinit var fileList: List<String>

    private var KEY_TEXTPSS: String = "TEXTPSS"
    private var CUSTOM_DIALOG_ID: Int = 0*/

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
        //openDialogBtn = findViewById(R.id.open_explorer_btn)

        cancelBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        submitBtn.setOnClickListener {
            nameInput = findViewById(R.id.event_name_input)

            val name = nameInput.text.toString()

            if ((name.length in 4..49) && (timePickerHour in 1..24) && (timePickerMinute in 1..60)) {
                database =
                    FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/")
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

        /*openDialogBtn.setOnClickListener {
            @Suppress("DEPRECATION")
            showDialog(CUSTOM_DIALOG_ID)
        }

        @Suppress("DEPRECATION")
        root = File(Environment.getExternalStorageDirectory().absolutePath)*/
    }

    /*private lateinit var dialog: Dialog
    override fun onCreateDialog(id: Int): Dialog {
        super.onCreateDialog(id)

        when (id) {
            CUSTOM_DIALOG_ID -> {
                dialog = Dialog(AddEventActivity)
                dialog.setContentView(R.layout.dialoglayout)
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true)
                dialog.setCanceledOnTouchOutside(true)

                textFolder = dialog.findViewById((R.id.folder))
                upBtn = dialog.findViewById(R.id.up_btn)

                upBtn.setOnClickListener {
                    ListDir(curFolder.parentFile)
                }

                dialogListView = dialog.findViewById(R.id.dialog_list)
                dialogListView.setOnItemClickListener{parent, view, position, id ->
                   val selectedFile = File(fileList[position])
                    if(selectedFile.isDirectory){
                        ListDir(selectedFile)
                    }else{
                        Toast.makeText(this.AddEventActivity, selectedFile.ToString() + " selected",
                        Toast.LENGTH_LONG).show()
                        dismissDialog(CUSTOM_DIALOG_ID)
                    }
                }
            }
        }
        return  dialog
    }

    override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        super.onPrepareDialog(id, dialog)

    }*/

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