package com.example.alarms

import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timeInMilliSeconds: Long = 0
        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)

        applicationContext.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        startTimeText.setOnClickListener {
            // Get Current Time
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minuteOfHour)
                    calendar.set(Calendar.SECOND, 0)

                    val amPm = if (hourOfDay < 12) "am" else "pm"
                    val formattedTime = String.format("%02d:%02d %s", hourOfDay, minuteOfHour, amPm)
                    startTimeText.text = formattedTime

                    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    val formattedDate = sdf.format(calendar.time)
                    val date = sdf.parse(formattedDate)
                    timeInMilliSeconds = date.time
                }, hour, minute, false)
            timePickerDialog.show()
        }

        setAlarm.setOnClickListener {
            if (timeInMilliSeconds.toInt() != 0) {
                Toast.makeText(this, "Alarm has been set!", Toast.LENGTH_LONG).show()

                val sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    ?: return@setOnClickListener
                with(sharedPref.edit()) {
                    putLong("timeInMilli", timeInMilliSeconds)
                    apply()
                }
                Utils.setAlarm(this, timeInMilliSeconds)
            } else {
                Toast.makeText(this, "Please enter the time first!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
