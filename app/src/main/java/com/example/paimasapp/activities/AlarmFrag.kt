package com.example.paimasapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.paimasapp.R

class AlarmFrag: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.alarm_fragment, container, false)

        // Classy names..
        val buttonOfScheduling = myView.findViewById(R.id.SheduleButton) as Button
        val pickerOfTimes = myView.findViewById(R.id.timePicker) as TimePicker

        buttonOfScheduling.setOnClickListener {
            val ma = activity as MainActivity
            ma.setTime(pickerOfTimes.hour, pickerOfTimes.minute)
            this.dismiss()
        }

        return myView
    }



}