package com.example.paimasapp.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.paimasapp.R
import com.example.paimasapp.background.handlers.SaveAlarmTimeHandler
// Comment out to run sans Phidgets
import com.example.paimasapp.background.services.PhidgetService
import com.example.paimasapp.ui.fragments.AlarmFrag
import com.example.paimasapp.ui.fragments.InstructionsFrag
import com.phidget22.*

class MainActivity : AppCompatActivity() {

//    private val lcd = LCD()

    // Comment out to run sans Phidgets
    private val v0 = VoltageInput()

    // Comment out to run sans Phidgets
    private var boxOpen : Boolean = true

    private lateinit var btnOpenCards: Button

    private val br : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("Server", "Server Message Received")
            if(p1 != null) {
                if(p1.action == "activate_alarm") {
                    btnOpenCards.visibility = View.VISIBLE
                } else if (p1.action == "deactivate_alarm") {
                    btnOpenCards.visibility = View.INVISIBLE
                } else if (p1.action == "alarm_set") {
                    val sath = SaveAlarmTimeHandler(this@MainActivity)
                    "Alarm Set: ${sath.getHour()}:${String.format("%02d", sath.getMin())}"
                        .also { findViewById<TextView>(R.id.timeDisplay).text = it }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = "PAMIAS"

        val button = findViewById<Button>(R.id.setAlarm)
        button.setOnClickListener{
            setAlarmButton()
        }

        val deactivateButton = findViewById<Button>(R.id.btn_deactivate)
        deactivateButton.setOnClickListener { onDisableClick() }

        btnOpenCards = findViewById<Button>(R.id.btnCards)
        btnOpenCards.setOnClickListener { onOpenCardView() }

        btnOpenCards.visibility = if(SaveAlarmTimeHandler(this).isActive()) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        val intentFilter = IntentFilter("server_start")
        intentFilter.addAction("activate_alarm")
        intentFilter.addAction("deactivate_alarm")
        intentFilter.addAction("alarm_set")
        registerReceiver(br, intentFilter)

        // Comment out to run sans Phidgets
        startService(Intent(this, PhidgetService::class.java))

    }

    override fun onDestroy() {
        super.onDestroy()
        // Comment out to run sans Phidgets
        v0.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.action_one) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.action_two) {
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
//            return true
        }
        if (id == R.id.action_three) {
            Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
//            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setTime(hours: Int, min: Int) {
        val timeDisplay = findViewById<TextView>(R.id.timeDisplay)

        "Alarm Set: $hours:${String.format("%02d", min)}".also { timeDisplay.text = it }

        val saveData = SaveAlarmTimeHandler(this)
        saveData.setAlarm(hours, min)
    }

    private fun setAlarmButton() {
        val alarmFrag = AlarmFrag()
        alarmFrag.show(supportFragmentManager, "Select time")
    }

    private fun onDisableClick() {
        val intent = Intent()
        intent.action = "deactivate_alarm"
        sendBroadcast(intent)
    }

    private fun onOpeningInstructions() {
        val instructionsFragment = InstructionsFrag()
        instructionsFragment.show(supportFragmentManager, "What goes in here?")
    }

    private fun onOpenCardView() {
        val intent = Intent(this, CardHolderActivity::class.java)
        this.startActivity(intent)
    }

}
