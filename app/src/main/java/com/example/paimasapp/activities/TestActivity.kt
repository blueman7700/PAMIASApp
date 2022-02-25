package com.example.paimasapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.paimasapp.R
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_picker_fragment)

        val timeText: TextView = findViewById((R.id.textView))
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        timeText.setText(currentDate)

    }

}