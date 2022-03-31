package com.example.paimasapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import com.example.paimasapp.R
import com.phidget22.CurrentInput
import com.phidget22.PhidgetException
import com.phidget22.VoltageRatioInput
import com.phidget22.VoltageRatioInputVoltageRatioChangeListener
import java.util.*
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhysicalInteractionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhysicalInteractionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var pbTarget: ProgressBar
    private lateinit var pbCurrent: ProgressBar

    private lateinit var btnConfirm: Button

    private val v0 = VoltageRatioInput()

    private val voltageRatioChangeListener = VoltageRatioInputVoltageRatioChangeListener {
        val newVal = (100 * it.voltageRatio).roundToInt()

        Log.d("slider", (100 * it.voltageRatio).roundToInt().toString())

        this.requireActivity().runOnUiThread {
            pbCurrent.progress = newVal
        }
    }

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
        return inflater.inflate(R.layout.fragment_physical_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pbTarget = this.requireView().findViewById(R.id.pbTarget)
        pbCurrent = this.requireView().findViewById(R.id.pbCurrent)
        btnConfirm = this.requireView().findViewById(R.id.btnClose)

        btnConfirm.setOnClickListener { checkIfCanContinue() }

        pbTarget.progress = Random().nextInt(100)

        try {
            v0.channel = 7
            v0.deviceSerialNumber = 39831
            v0.open()
            v0.addVoltageRatioChangeListener(voltageRatioChangeListener)
        } catch (e: PhidgetException) {
            e.printStackTrace()
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhysicalInteractionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhysicalInteractionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun checkIfCanContinue() {
        if (pbCurrent.progress > (pbTarget.progress - 5)
            && pbCurrent.progress < (pbTarget.progress + 5)) {
            //TODO: notify complete
            Log.d("progress", "Physical Interaction Complete")
            val intent = Intent()
            intent.action = "physical"
            this.requireActivity().sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        v0.close()
    }
}