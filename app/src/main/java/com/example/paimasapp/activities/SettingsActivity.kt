package com.example.paimasapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paimasapp.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.settings, SettingsFragment())
//                .commit()
//        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        // I don't think it's this!
        setContentView(R.layout.activity_main)
//        super.onBackPressed()
    }

}