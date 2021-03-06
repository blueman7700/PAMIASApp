package com.example.paimasapp.background.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.example.paimasapp.background.handlers.SaveAlarmTimeHandler
import com.phidget22.*

class PhidgetService : Service() {

    val ip = "192.168.56.1"
    val deviceNumber = 39831
    val port = 5661
    private var oldtime: Long = 0
    private val lcd = LCD()
    private val v0 = VoltageInput()
    private val d0 = DigitalOutput()
    private val d1 = DigitalOutput()
    private var boxOpen = false
    private val alarmHandler: SaveAlarmTimeHandler = SaveAlarmTimeHandler(this)

    private val mPhidgetActionReceiver : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            if (p1 != null) {
                when (p1.action) {
                    "activate_alarm" -> {
                        Log.d("Message_Received", "Alarm Activated")
                        d0.state = true
                        d1.state = true
                    }
                    "deactivate_alarm" -> {
                        Log.d("Message_Received", "Alarm Deactivated")
                        d0.state = false
                        d1.state = false
                        lcd.clear()
                    }
                    "print_lcd" -> {
                        Log.d("Message_Received", "Service received print request")
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
        registerReceiver(mPhidgetActionReceiver, intentFilter)

        try {
            // Enable server discovery to list remote Phidgets
            this.getSystemService(Context.NSD_SERVICE)
            Net.enableServerDiscovery(ServerType.DEVICE_REMOTE)

            Net.addServer("", ip, port, "", 0)
            Log.d("srv_info", "Server Started!")

            lcd.deviceSerialNumber = deviceNumber
            lcd.open(1000)
            lcd.backlight = 1.0
            lcd.contrast = 0.5

            v0.deviceSerialNumber = deviceNumber
            v0.channel = 0

            d0.deviceSerialNumber = deviceNumber
            d0.channel = 0

            d1.deviceSerialNumber = deviceNumber
            d1.channel = 1

            v0.open(1000)
            d0.open(1000)
            d1.open(1000)

            v0.voltageChangeTrigger = 0.1
            v0.addAttachListener(attachListener)
            v0.addDetachListener(detachListener)
            v0.addVoltageChangeListener(voltageChangeListener)

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
            val currtime = System.currentTimeMillis()

            if(boxOpen) {
                oldtime = currtime
                lcd.backlight = 1.0
            } else {
                lcd.backlight = 0.0
                if((currtime - oldtime) < 5000 ){
                    alarmHandler.snooze()
                }
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