package com.example.paimasapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        val ans1 = this.requireView().findViewById<Button>(R.id.Answer1)
        val ans2 = this.requireView().findViewById<Button>(R.id.Answer2)
        val ans3 = this.requireView().findViewById<Button>(R.id.Answer3)
        val ans4 = this.requireView().findViewById<Button>(R.id.Answer4)

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
                    ans1.text = arr[0].toString()
                }1 -> {
                    ans2.text = arr[1].toString()
                }2 -> {
                    ans3.text = arr[2].toString()
                }3-> {
                    ans4.text = arr[3].toString()
                }
            }
            index++
        }

        ans1.setOnClickListener{
            if(ans1.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
            } else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        ans2.setOnClickListener{
            if(ans2.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        ans3.setOnClickListener{
            if(ans3.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
        ans4.setOnClickListener{
            if(ans4.text == answer.toString()){
                //if correct do...
                Log.d("Ans","This is correct")
            }else {
                Toast.makeText(this.requireActivity(), "Incorrect", Toast.LENGTH_SHORT).show()
                Log.d("Ans","Incorrect")
            }
        }
    }

}