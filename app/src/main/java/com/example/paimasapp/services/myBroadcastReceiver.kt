package com.example.paimasapp.services

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class myBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent : Intent?) {
        if (intent!!.action.equals("com.tester.alarmmanager")){
            val b = intent.extras
            Toast.makeText(context, b!!.getString("message"), Toast.LENGTH_LONG).show()
            Log.d("test", "Alarm Trigger")
            //Put alarm trigger code here
            val broadcastIntent = Intent()
            val alarmIntent = Intent()
            alarmIntent.setAction("activate_alarm")
            broadcastIntent.putExtra("l1", "Alarm On!")
            broadcastIntent.putExtra("l2", "Get Rickrolled")
            broadcastIntent.setAction("print_lcd")
            context!!.sendBroadcast(broadcastIntent)
            context!!.sendBroadcast(alarmIntent)
        }
    }
}