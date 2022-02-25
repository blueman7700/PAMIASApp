package com.example.paimasapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PhidgetService : Service() {

    val ip = "x.x.x.x"
    val port = 1111

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}