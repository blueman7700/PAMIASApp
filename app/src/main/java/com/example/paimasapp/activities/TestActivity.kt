package com.example.paimasapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paimasapp.R
import java.util.*

class TestActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_frag)

       /* val timePicker: TimePicker = findViewById(R.id.timePicker)
        val textClock: TextClock = findViewById(R.id.textClock)
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(textClock.text.toString() == alarmTime(timePicker)){
                    Log.d("Alarm","This Is An Alarm")
                }
            }
        }, 0, 1000)*/
    }

   /* private fun alarmTime(timePicker : TimePicker): String {
        var alarmHour : Int = timePicker.hour
        var alarmMinutes : Int = timePicker.minute
        var stringAlarmTime : String = ""

        if(alarmHour>12){
            alarmHour -= 12
            stringAlarmTime = "$alarmHour:$alarmMinutes PM"
        } else {
            stringAlarmTime = "$alarmHour:$alarmMinutes AM"
        }

        return stringAlarmTime


    }*/
}