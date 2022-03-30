package com.example.paimasapp.ui.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.paimasapp.R
import com.phidget22.PhidgetException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [SensorInteractionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorInteractionFragment : Fragment(), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mSensorManager: SensorManager
    var mSensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor_interaction, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SensorInteractionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SensorInteractionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mSensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)

    }
    var dtOld = SystemClock.elapsedRealtimeNanos()
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                /*xTextView.setText("X = " + String.format("%.3f", event.values.get(0)))
                yTextView.setText("Y = " + String.format("%.3f", event.values.get(1)))
                zTextView.setText("Z = " + String.format("%.3f", event.values.get(2)))
                aTextView.setText("A = " + String.format("%.3f", event.values.get(3)))*/
                try {
                    val gyro: Float = p0.values[1]
                    var dt: Double = (p0.timestamp - dtOld).toDouble()
                    dt /= 1000000.0
                    val angle: Double = +gyro * dt
                    val acc_data: Float = p0.values[3]
                    dtOld = p0.timestamp
                    val targetPos = 0.98 * angle + 0.02 * acc_data
                    Log.d("Test", targetPos.toString())


                } catch (phidgetException: PhidgetException) {
                    phidgetException.printStackTrace()
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}