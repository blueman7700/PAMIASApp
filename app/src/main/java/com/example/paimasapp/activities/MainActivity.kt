package com.example.paimasapp.activities


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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
            setupPhidgets()
        }

    }

    private val attachListener = AttachListener {
        Log.d("Attach Listener", it.toString())
    }

    private val detachListener = DetachListener {
        Log.d("Detach Listener", it.toString())
    }

    private val voltageChangeListener = VoltageInputVoltageChangeListener {
        Log.d("Voltage Change", it.voltage.toString())
        try {

            boxOpen = !(it.voltage > 3f || it.voltage < 2f)

            lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 0, "")
            lcd.flush()
            lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 0, "Box is open: $boxOpen ")
            lcd.flush()
        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.setAlarm)
        button.setOnClickListener{
            setAlarmButton()
        }
        
        val intentFilter = IntentFilter("server_start")
        registerReceiver(br, intentFilter)
        startService(Intent(this, PhidgetService::class.java))
    }

    private fun setupPhidgets() {
        try {
            lcd.deviceSerialNumber = 39831
            v0.deviceSerialNumber = 39831
            v0.channel = 0

            lcd.open(5000)
            v0.open(5000)

            lcd.backlight = 1.0
            lcd.contrast = 0.5

            v0.addAttachListener(attachListener)
            v0.addDetachListener(detachListener)
            v0.addVoltageChangeListener(voltageChangeListener)

            v0.voltageChangeTrigger = 0.1

            lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 0, "Hello...")
            lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 1, "voltage: ${v0.voltage}")
            lcd.flush()

        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        v0.close()
        lcd.close()
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

    fun SetTime(hours:Int, min:Int){
        var timeDisplay = findViewById<TextView>(R.id.timeDisplay)
        timeDisplay.text = "Alarm Set: $hours:$min"

        val saveData = SaveData(this)
        saveData.setAlarm(hours, min)
    }

    fun setAlarmButton(){
        val alarmfrag = AlarmFrag()
        alarmfrag.show(supportFragmentManager, "Select time")
    }
}
