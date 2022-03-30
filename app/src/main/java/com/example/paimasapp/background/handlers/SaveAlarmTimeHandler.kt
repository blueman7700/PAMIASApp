package com.example.paimasapp.background.handlers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.paimasapp.background.services.myBroadcastReceiver
import java.util.*


class SaveAlarmTimeHandler(private val context: Context) {

    companion object {
        @JvmStatic
        private var setHour = 0
        @JvmStatic
        private var setMin = 0
        @JvmStatic
        private var isActive = false
        @JvmStatic
        private var storedIntent: PendingIntent?= null
    }

    fun setAlarm(hour: Int, min: Int) {
        setHour = hour
        setMin = min
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, myBroadcastReceiver::class.java)

        intent.putExtra("message", "alarm time")
        Log.d("test", "Set Intent")
        intent.action="set_alarm"

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            111,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        storedIntent = pendingIntent

        am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Log.d("test", "Alarm Set")

        val msgIntent = Intent()
        msgIntent.action = "print_lcd"
        msgIntent.putExtra("l1", "Alarm Set For:")
        msgIntent.putExtra("l2", String.format("%02d:%02d", hour, min))
        context.sendBroadcast(msgIntent)
    }

    fun snooze() {

        if(isActive) {
            this.deactivate()
            var newMin = setMin + 5
            var newHour = setHour
            if(newMin >= 60) {
                //if the new minutes are >= 60, then increment the hour
                newMin -= 60
                newHour += 1
            }
            this.setAlarm(newHour, newMin)
        }
    }

    fun deactivate() {
        isActive = false
        val intent = Intent()
        intent.action = "deactivate_alarm"
        context.sendBroadcast(intent)
    }

    fun cancelSetAlarm() {
        if(storedIntent != null) {

            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(storedIntent)
            storedIntent!!.cancel()
        }
    }

    fun getHour(): Int {
        return setHour
    }

    fun getMin(): Int {
        return setMin
    }

    fun setActive(state: Boolean) {
        isActive = state
    }
}