package com.example.paimasapp.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.phidget22.Net
import com.phidget22.PhidgetException
import com.phidget22.ServerType

class PhidgetService : Service() {

    private val ip = "137.44.116.203"
    private val port = 5661

    private val br : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("Message_Received", "Service received message")
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        try {
            //Enable server discovery to list remote Phidgets
            this.getSystemService(Context.NSD_SERVICE)
            Net.enableServerDiscovery(ServerType.DEVICE_REMOTE)

            Net.addServer("", ip, port, "", 0)
            Log.d("srv_info", "Server Started!")
            sendBroadcast()
        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Net.disableServerDiscovery(ServerType.DEVICE_REMOTE)
    }

    private fun sendBroadcast() {
        val intent = Intent()
        intent.setAction("server_start")
        sendBroadcast(intent)
    }
}