package com.example.paimasapp.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.paimasapp.R
import com.example.paimasapp.ui.activities.MainActivity
import com.phidget22.*

class AlarmFrag: DialogFragment() {

    private val v0 = VoltageRatioInput()
    private val din0 = DigitalInput()
    private val din1 = DigitalInput()
    private val dout0 = DigitalOutput()

    private lateinit var pickerOfTimes: TimePicker
    private var currHour: Int = 0
    private var currMin: Int = 0
    private var isHour = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.alarm_fragment, container, false)

        // Classy names..
        val buttonOfScheduling = myView.findViewById(R.id.SheduleButton) as Button
        pickerOfTimes = myView.findViewById(R.id.timePicker) as TimePicker
        currHour = pickerOfTimes.hour

        buttonOfScheduling.setOnClickListener {
            val ma = activity as MainActivity

            ma.setTime(pickerOfTimes.hour, pickerOfTimes.minute)
            this.dismiss()
        }
        try {
            v0.deviceSerialNumber = 39831
            v0.channel = 2

            dout0.channel = 2
            din0.channel = 6
            din1.channel = 7

            v0.open(5000)
            dout0.open(5000)
            din0.open(5000)
            din1.open(5000)

            v0.addAttachListener(attachListener)
            v0.addDetachListener(detachListener)
            v0.addVoltageRatioChangeListener(voltageChangeListener)

            din0.addStateChangeListener(digitalInputStateChangeListener)

        } catch (e: PhidgetException) {
        e.printStackTrace()
        }

        return myView
    }


    private val attachListener = AttachListener {
        Log.d("Attach Listener", it.toString())
    }

    private val detachListener = DetachListener {
        Log.d("Detach Listener", it.toString())
    }

    private val voltageChangeListener = VoltageRatioInputVoltageRatioChangeListener {
        try {
            var newTime: Double
            if(isHour) {
                newTime = Math.floor(it.voltageRatio*24)
                if (newTime > 23){
                    newTime = 23.0
                }
                if( newTime.toInt() != currHour){
                    currHour = newTime.toInt()
                    updateDTPTime()
                    Log.d("Voltage Change", it.voltageRatio.toString())
                }
            } else {
                newTime = Math.floor(it.voltageRatio*60)
                if (newTime > 59){
                    newTime = 59.0
                }
                if( newTime.toInt() != currMin){
                    currMin = newTime.toInt()
                    updateDTPTime()
                    Log.d("Voltage Change", it.voltageRatio.toString())
                }
            }
        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    private val digitalInputStateChangeListener = DigitalInputStateChangeListener {
        try {
            isHour = din0.state
            Log.d("Digital State Change", "din0 state : ${din0.state}")
        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    private fun updateDTPTime() {

        this.requireActivity().runOnUiThread {
            pickerOfTimes.hour = currHour
            pickerOfTimes.minute = currMin
        }
    }

    /*override fun onResume() {
        pickerOfTimes.hour = curHour
        super.onResume()
    }*/

    override fun onDestroy() {
        v0.close()
        din0.close()
        din1.close()
        dout0.state = false
        dout0.close()
        super.onDestroy()
    }
}