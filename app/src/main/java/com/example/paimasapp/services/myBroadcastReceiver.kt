package com.example.paimasapp.services

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
        }
    }
}