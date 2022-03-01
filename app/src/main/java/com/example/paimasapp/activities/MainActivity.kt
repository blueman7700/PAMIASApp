package com.example.paimasapp.activities


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.paimasapp.R
import com.example.paimasapp.services.PhidgetService
import com.phidget22.*

class MainActivity : AppCompatActivity() {

    private val lcd = LCD()
    private val v0 = VoltageInput()

    private var boxOpen : Boolean = true

    private val br : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("Server", "Server Message Received")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.setAlarm)
        button.setOnClickListener{
            setAlarmButton()
        }
        val deactivateButton = findViewById<Button>(R.id.btn_deactivate)
        deactivateButton.setOnClickListener { onDisableClick() }
        val intentFilter = IntentFilter("server_start")
        registerReceiver(br, intentFilter)
        startService(Intent(this, PhidgetService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        v0.close()
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
            setAlarmButton()
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

    fun setTime(hours:Int, min:Int){
        val timeDisplay = findViewById<TextView>(R.id.timeDisplay)

        timeDisplay.text = "Alarm Set: $hours:${String.format("%02d", min)}"

        val saveData = SaveData(this)
        saveData.setAlarm(hours, min)
    }

    private fun setAlarmButton(){
        val alarmFrag = AlarmFrag()
        alarmFrag.show(supportFragmentManager, "Select time")
    }

    private fun onDisableClick(){

        val intent = Intent()
        intent.setAction("deactivate_alarm")
        sendBroadcast(intent)
    }
}
