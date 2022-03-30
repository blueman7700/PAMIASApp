package com.example.paimasapp.background.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.paimasapp.background.handlers.SaveAlarmTimeHandler

class myBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent : Intent?) {
        Log.d("receiver", "detected message: ${intent!!.action}")
        if (intent!!.action.equals("set_alarm")) {
            val b = intent.extras
            Toast.makeText(context, b!!.getString("message"), Toast.LENGTH_LONG).show()
            Log.d("test", "Alarm Trigger")

            // Put alarm trigger code here
            val broadcastIntent = Intent()
            val alarmIntent = Intent()
            SaveAlarmTimeHandler(context!!).setActive(true)

            alarmIntent.action = "activate_alarm"
            broadcastIntent.putExtra("l1", "Alarm On!")
            broadcastIntent.putExtra("l2", "Get Rickrolled")
            broadcastIntent.action = "print_lcd"

            context!!.sendBroadcast(broadcastIntent)
            context!!.sendBroadcast(alarmIntent)
        }
    }
}