package com.example.paimasapp.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.paimasapp.R
import com.example.paimasapp.data.ZoomOutCardTransformer
import com.example.paimasapp.ui.fragments.MentalInteractionFragment
import com.example.paimasapp.ui.fragments.PhysicalInteractionFragment
import com.example.paimasapp.ui.fragments.SensorInteractionFragment

private const val NUM_CARDS = 3

class CardHolderActivity : FragmentActivity() {

    private val interactionReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1 != null) {
                when(p1.action) {
                    "mental" -> {
                        mPager.currentItem += 1
                    }
                    "physical" -> {
                        mPager.currentItem += 1
                    }
                    "sensor" -> {
                        val intent = Intent()
                        intent.action = "deactivate_alarm"
                        sendBroadcast(intent)
                        val message = Intent()
                        message.action = "print_lcd"
                        message.putExtra("l1", "Alarm Off")
                        message.putExtra("l2", "Good Morning!")
                        sendBroadcast(message)
                        finish()
                    }
                }
            }
        }

    }

    private inner class CardPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        override fun getItemCount(): Int = NUM_CARDS



        /**
         * Provide a new Fragment associated with the specified position.
         *
         *
         * The adapter will be responsible for the Fragment lifecycle:
         *
         *  * The Fragment will be used to display an item.
         *  * The Fragment will be destroyed when it gets too far from the viewport, and its state
         * will be saved. When the item is close to the viewport again, a new Fragment will be
         * requested, and a previously saved state will be used to initialize it.
         *
         * @see ViewPager2.setOffscreenPageLimit
         */
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
                    MentalInteractionFragment()
                }
                1 -> {
                    PhysicalInteractionFragment()
                }
                2 -> {
                    SensorInteractionFragment()
                }
                else -> {
                    MentalInteractionFragment()
                }
            }
        }
    }

    private lateinit var mPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_holder)
        setActionBar(findViewById(R.id.toolbar))

        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "Cards"

        mPager = findViewById(R.id.pager)
        mPager.setPageTransformer(ZoomOutCardTransformer())

        val mAdapter = CardPagerAdapter(this)
        mPager.adapter = mAdapter
        mPager.isUserInputEnabled = false

        val intentFilter = IntentFilter("mental")
        intentFilter.addAction("physical")
        intentFilter.addAction("sensor")
        registerReceiver(interactionReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(interactionReceiver)
    }

    override fun onBackPressed() {

        if (mPager.currentItem == 0) {
            super.onBackPressed()
            finish()
        } else {
            mPager.currentItem -= 1
        }
    }
}