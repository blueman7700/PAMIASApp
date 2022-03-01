package com.example.paimasapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.paimasapp.R

class AlarmFrag: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var myView = inflater.inflate(R.layout.alarm_frag, container, false)
        var buShedge = myView.findViewById(R.id.SheduleButton) as Button
        var timerpicker = myView.findViewById(R.id.timePicker) as TimePicker

        buShedge.setOnClickListener {
            val ma = activity as MainActivity
            ma.setTime(timerpicker.hour, timerpicker.minute)
            this.dismiss()
        }

        return myView
    }



}