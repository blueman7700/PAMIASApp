package com.example.paimasapp.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.phidget22.*

class PhidgetService : Service() {

    val ip = "137.44.116.242"
    val deviceNumber = 39831
    val port = 5661
    
    private val lcd = LCD()
    private val v0 = VoltageInput()
    private val d0 = DigitalOutput()
    private var boxOpen = false

    private val mBroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            if (p1 != null) {
                when /* switch */ (p1.action) {
                    /* case: */ "activate_alarm" -> {
                        Log.d("Message_Received", "Alarm Activated")
                        d0.state = true

                    }
                    /* case: */ "deactivate_alarm" -> {
                        Log.d("Message_Received", "Alarm Deactivated")
                        d0.state = false

                    }
                    /* case: */ "print_lcd" -> {
                        Log.d("Message_Received", "Service received message")
                        val l1: String? = p1.getStringExtra("l1")
                        val l2: String? = p1.getStringExtra("l2")

                        lcd.clear()

                        if (l1 != null) lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 0, l1)
                        if (l2 != null) lcd.writeText(LCDFont.DIMENSIONS_5X8, 0, 1, l2)

                        // Oh fuck, we forgot to flush again
                        lcd.flush()
                    }
                }
            }
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

        val intentFilter = IntentFilter("print_lcd")
        intentFilter.addAction("activate_alarm")
        intentFilter.addAction("deactivate_alarm")
        registerReceiver(mBroadcastReceiver, intentFilter)

        try {
            // Enable server discovery to list remote Phidgets
            this.getSystemService(Context.NSD_SERVICE)
            Net.enableServerDiscovery(ServerType.DEVICE_REMOTE)

            Net.addServer("", ip, port, "", 0)
            Log.d("srv_info", "Server Started!")

            lcd.deviceSerialNumber = deviceNumber
            lcd.open(5000)
            lcd.backlight = 1.0
            lcd.contrast = 0.5

            v0.deviceSerialNumber = deviceNumber
            v0.channel = 0

            d0.deviceSerialNumber = deviceNumber
            d0.channel = 0

            v0.open(5000)
            d0.open(5000)

            v0.addAttachListener(attachListener)
            v0.addDetachListener(detachListener)
            v0.addVoltageChangeListener(voltageChangeListener)

            v0.voltageChangeTrigger = 0.1

            sendBroadcast()
        } catch (e: PhidgetException) {
            e.printStackTrace()
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

            if(boxOpen) {
                lcd.backlight = 1.0
            } else {
                lcd.backlight = 0.0
            }

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
        intent.action = "server_start"
        sendBroadcast(intent)
    }

}