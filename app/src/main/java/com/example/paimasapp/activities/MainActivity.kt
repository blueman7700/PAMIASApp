package com.example.paimasapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.paimasapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val button = findViewById<Button>(R.id.setAlarm)
        button.setOnClickListener{
            val intent = Intent(this, TestActivity::class.java
            )
            startActivity(intent)
        }
    }



}