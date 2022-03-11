package com.example.paimasapp.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.paimasapp.services.myBroadcastReceiver
import java.util.*


class SaveData(context: Context) {

    private var context: Context?= context

    fun setAlarm(hour: Int, min: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)

        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, myBroadcastReceiver::class.java)

        intent.putExtra("message", "alarm time")
        Log.d("test", "Set Intent")
        intent.action="com.tester.alarmmanager"

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 111, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Log.d("test", "Alarm Set")

        val msgIntent = Intent()
        msgIntent.setAction("print_lcd")
        msgIntent.putExtra("l1", "Alarm Set For:")
        msgIntent.putExtra("l2", String.format("%02d:%02d", hour, min))
        context!!.sendBroadcast(msgIntent)
    }

}