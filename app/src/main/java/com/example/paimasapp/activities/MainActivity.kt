package com.example.paimasapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {
            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.action_two) {
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
            return true
        }
        if (id == R.id.action_three) {
            Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}

