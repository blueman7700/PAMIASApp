package com.example.paimasapp.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.paimasapp.R
import com.phidget22.*

class AlarmFrag: DialogFragment() {

    private val v0 = VoltageRatioInput()
    private val d0 = DigitalInput()
    private val d1 = DigitalInput()
    private val dOut = DigitalOutput()


    private lateinit var pickerOfTimes: TimePicker
    private var curHour: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.alarm_fragment, container, false)

        // Classy names..
        val buttonOfScheduling = myView.findViewById(R.id.SheduleButton) as Button
        pickerOfTimes = myView.findViewById(R.id.timePicker) as TimePicker
        curHour = pickerOfTimes.hour

        buttonOfScheduling.setOnClickListener {
            val ma = activity as MainActivity

            ma.setTime(pickerOfTimes.hour, pickerOfTimes.minute)
            this.dismiss()
        }
        try {
            v0.deviceSerialNumber = 39831
            v0.channel = 2

            v0.open(5000)

            v0.addAttachListener(attachListener)
            v0.addDetachListener(detachListener)
            v0.addVoltageRatioChangeListener(voltageChangeListener)

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
            var newTime = Math.floor(it.voltageRatio*24)
            if (newTime > 23){
                newTime = 23.0
            }
            if( newTime.toInt() != curHour){
                curHour = newTime.toInt()
                Log.d("Voltage Change", it.voltageRatio.toString())

            }

        } catch (e: PhidgetException) {
            e.printStackTrace()
        }
    }

    /*override fun onResume() {
        pickerOfTimes.hour = curHour
        super.onResume()
    }*/

    override fun onDestroy() {
        v0.close()
        super.onDestroy()
    }
}