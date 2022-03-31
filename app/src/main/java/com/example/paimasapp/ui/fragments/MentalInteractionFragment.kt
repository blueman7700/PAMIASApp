package com.example.paimasapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.paimasapp.R
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentalInteractionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentalInteractionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnAns1: Button
    private lateinit var btnAns2: Button
    private lateinit var btnAns3: Button
    private lateinit var btnAns4: Button
    private lateinit var btnContinue: Button


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
        return inflater.inflate(R.layout.fragment_mental_interaction, container, false)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentalInteractionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentalInteractionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAns1 = this.requireView().findViewById(R.id.Answer1)
        btnAns2 = this.requireView().findViewById(R.id.Answer2)
        btnAns3 = this.requireView().findViewById(R.id.Answer3)
        btnAns4 = this.requireView().findViewById(R.id.Answer4)

        btnContinue = this.requireView().findViewById(R.id.btnClose)
        btnContinue.isEnabled = false
        btnContinue.setOnClickListener { continueToNextInteraction() }

        val question = this.requireView().findViewById<TextView>(R.id.questionText)

        val max = 100
        val min = 1
        val random1 =  Random().nextInt(max-min) + min
        val random2 =  Random().nextInt(max-min) + min

        val x = Random().nextInt(2)

        var answer: Int = 0

        when(x){
            0 -> {
                answer = random1 + random2
                question.text = "$random1 + $random2"
            }1 -> {
                answer = random1 - random2
                question.text = "$random1 - $random2"
            }2 -> {
                answer = random1 * random2
                question.text = "$random1 x $random2"
            }
        }

        val arr  = intArrayOf(answer, answer + 10, answer - 10, random1 + answer)

        arr.shuffle()

        var index = 0
        while (index < arr.size) {
            when(index){
                0 -> {
                    btnAns1.text = arr[0].toString()
                }1 -> {
                    btnAns2.text = arr[1].toString()
                }2 -> {
                    btnAns3.text = arr[2].toString()
                }3-> {
                    btnAns4.text = arr[3].toString()
                }
            }
            index++
        }

        btnAns1.setOnClickListener{
            if(btnAns1.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
                handleCorrectAnswer()
            } else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        btnAns2.setOnClickListener{
            if(btnAns2.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
                handleCorrectAnswer()
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        btnAns3.setOnClickListener{
            if(btnAns3.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
                handleCorrectAnswer()
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        btnAns4.setOnClickListener{
            if(btnAns4.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
                handleCorrectAnswer()
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
    }

    private fun handleCorrectAnswer() {
        //disable all buttons
        btnAns1.isEnabled = false
        btnAns2.isEnabled = false
        btnAns3.isEnabled = false
        btnAns4.isEnabled = false

        btnContinue.isEnabled = true
    }

    private fun continueToNextInteraction() {
        val intent = Intent()
        intent.action = "mental"
        this.requireActivity().sendBroadcast(intent)
    }
}