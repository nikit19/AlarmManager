package com.example.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object Utils {

    fun setAlarm(context: Context, timeOfAlarm: Long) {

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(context
            , NotificationReceiver::class.java)

        val pIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            0
        )

        // Setting up AlarmManager
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < timeOfAlarm) {
            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pIntent
            )
        }
    }
}