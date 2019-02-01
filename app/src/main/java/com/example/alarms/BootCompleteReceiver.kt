package com.example.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // ideally we should be fetching the data from a database
            val sharedPref = context?.getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
            val timeInMilli = sharedPref.getLong("timeInMilli", 1)
            Utils.setAlarm(context, timeInMilli)
        }
    }
}