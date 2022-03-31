package com.example.paimasapp.ui.fragments

import android.content.Context
import android.content.Intent
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
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.paimasapp.R
import java.util.*

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
    private lateinit var accelText: TextView
    var mSensor: Sensor? = null
    var targetVal: Int = 0

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

        //I dont think this is attacking properly
        mSensorManager = this.requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        Log.d("nullCheck","SensorManager created?")

        accelText = this.requireView().findViewById(R.id.accelText)

        targetVal = Random().nextInt(68) - 34

        val taskText = "Rotate your device in the Z axis to $targetVal"
        val tText = this.requireView().findViewById<TextView>(R.id.taskText)
        tText.text = taskText
    }
    private var dtOld = SystemClock.elapsedRealtimeNanos().toDouble()
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if(p0.sensor.type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR){

                val gyro: Float = p0.values[2]
                var dt: Double = (p0.timestamp - dtOld)
                dt /= 1000000.0
                val angle: Double = (+gyro * dt).toDouble()
                val acc_data: Float = p0.values[3]
                dtOld = p0.timestamp.toDouble()

                val targetPos = 0.98 * angle + 0.02 * acc_data

                accelText.text = "Cur X: ${targetPos.toInt()}"
                if(targetPos.toInt() == targetVal){
                    Log.d("Trigger","Target threshold met")

                    val intent = Intent()
                    intent.action = "sensor"
                    this.requireActivity().sendBroadcast(intent)
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onPause() {
        super.onPause()
       // mSensorManager.unregisterListener(this)
    }


}