package com.example.eventcalendarapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonClass
import okhttp3.*
import java.io.IOException


class ListEventsActivity : AppCompatActivity() {

    /*@JsonClass(generateAdapter = true)
    data class Gist(var response: Map<String, GistFile>?)

    @JsonClass(generateAdapter = true)
    data class GistFile(var id: Int?, var date: String?, var time: String?, var timestamp:String?, val timezone:String?, val stage: String?, val week: String?,
                        val status: Map<String, Status>, var league: Map<String, League>?, var country: Map<String, Country>?, var teams: Map<String, Teams>, var scores: Map<String, Scores>?)

    @JsonClass(generateAdapter = true)
    data class Status(var long: String, var short: String, var timer: String?)

    @JsonClass(generateAdapter = true)
    data class League(val id: Int?, val name: String?, val type: String?, val season: String?, val logo: String?)

    @JsonClass(generateAdapter = true)
    data class Country(val id: Int?, val name: String?, var code: String?, var flag: String?)

    @JsonClass(generateAdapter = true)
    data class Teams(var home: Map<String, Home>, var away: Map<String, Away>)

    @JsonClass(generateAdapter = true)
    data class Home(var id:Int?, var name: String?, var logo: String?)

    @JsonClass(generateAdapter = true)
    data class Away(var id:Int?, var name: String?, var logo: String?)

    @JsonClass(generateAdapter = true)
    data class Scores(var home: Map<String, HomeScore>, var away: Map<String, AwayScore>?)

    @JsonClass(generateAdapter = true)
    data class HomeScore(var q1:Int?, var q2:Int?,var q3:Int?,var q4:Int?,var over_time:Int?, var total:Int?)

    @JsonClass(generateAdapter = true)
    data class AwayScore(var q1:Int?, var q2:Int?,var q3:Int?,var q4:Int?,var over_time:Int?, var total:Int?)*/

    @JsonClass(generateAdapter = true)
    data class Schedule(var game: Map<String, Games>)

    @JsonClass(generateAdapter = true)
    data class Games(var h_abrv: String, var v_abrv: String, var id: String, var dt: String, var r_reg:String, val is_lp:Boolean, var sg: Boolean)

    private lateinit var backBtn: Button
    private lateinit var eventList: ListView
    private lateinit var dbReference: DatabaseReference
    private lateinit var checkEvents: Query

    //private val moshi = Moshi.Builder().build()
    //private val gistJsonAdapter = moshi.adapter(Gist::class.java)

    //private lateinit var listOfEvents: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        eventList = findViewById(R.id.event_list)
        backBtn = findViewById(R.id.exit_list_of_events)

        val date = intent.getStringExtra("SELECTED_DATE")
        //val date = "2021-01-16"

        val client = OkHttpClient()
        fun run(){
            val request = Request.Builder()
                .url("http://data.nba.net/json/cms/2016/league/nba_games.json")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    //val arrayNbaSchedule= object: TypeToken<Array<>>() {}.type

                    val gson = Gson()
                    //val arrayTutorialType = object : TypeToken<Array<EventHelperClass>>() {}.type

                    var events: Schedule = gson.fromJson(response.body!!.string(), Schedule::class.java)

                    for ((key, value) in events.game) {
                        println(value.h_abrv)
                    }
                    //var parsedJson: Schedule = gson.fromJson(response.body!!.string(), Schedule::class.java)//arrayNbaSchedule)

                    //for( item in parsedJson)
                }
            })
        }

        run()
      /*  fun run() {
            val nbaApiGameDate =
                "https://api-basketball.p.rapidapi.com/games?league=12&season=2020-2021&date=$date"

            val request = Request.Builder()
                .url(nbaApiGameDate)
                .get()
                .addHeader("x-rapidapi-key", "50899ccfd7msh54f17ff75c62c18p157583jsnab622258e838")
                .addHeader("x-rapidapi-host", "api-basketball.p.rapidapi.com")
                .build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    //val arrayNbaSchedule= object: TypeToken<Array<Gist>>() {}.type
                    val gistJsonAdapter = moshi.adapter(GistFile::class.java)
                    val gist = gistJsonAdapter.fromJson(response.body!!.source())

                    for( item in gist!!.response!!)
                    {
                    for ((key,value) in gist!!.status!!){
                        println(value.long)
                    }
                        for ((key, value) in gist!!.teams!!)
                        {
                            for((key2,value2) in value.home){
                                println(value2.name)
                            }
                            for((key2,value2) in value.away){
                                println(value2.name)
                                //println("${value2.name} vs ${value2.away}")
                            }
                        }
                    //}

                    val arrayNbaSchedule= object: TypeToken<Array<NbaScheduleAdapter>>() {}.type

                    println(response.body!!.string())

                    val gson = Gson()
                   var parsedJson: Array<NbaScheduleAdapter> = gson.fromJson(response.body!!.string(), NbaScheduleAdapter::class.java)//arrayNbaSchedule)

                    println(parsedJson)
                }
            })
        }

        run()*/

        dbReference = FirebaseDatabase.getInstance("https://eventcalendar-d4ec6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Events")

        /*titleQuery: Query = dbReference.child("events").orderByChild("title").equalTo("value")
        for (appleSnapshot in dataSnapshot.getChildren()) {
            appleSnapshot.ref.removeValue()
        }*/

        checkEvents = dbReference

        checkEvents.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val listOfEvents = ArrayList<String>()
                    for (e in p0.children) {
                        val event = e.getValue(EventHelperClass::class.java)!!.ToString()

                        if (e.getValue(EventHelperClass::class.java)!!.getDate() == date) {
                            listOfEvents.add(event)
                        }
                        // listOfEventNames.add((eventName))
                    }
                    val arrayAdapter = ArrayAdapter(this@ListEventsActivity, R.layout.row, listOfEvents)
                    eventList.adapter = arrayAdapter
                }
            }
        })

        //eventList.setOnClick

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}