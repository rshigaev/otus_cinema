package com.rouming.cinema_for_you

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.rouming.cinema_for_you.MainActivity.Companion.DETAILED_FILM_NAME
import com.rouming.cinema_for_you.MainActivity.Companion.DETAILED_ID
import com.rouming.cinema_for_you.MainActivity.Companion.TAG
import java.util.*

private var am: AlarmManager? = null

class AddNotificationDialog( val layoutId:Int, val film:Film): DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        am = getSystemService(App.instance.applicationContext, AlarmManager::class.java)
        return inflater.inflate(layoutId, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setNotifButton = view.findViewById<Button>(R.id.date_time_set)
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val timePicker = view.findViewById<TimePicker>(R.id.time_picker)
        timePicker.setIs24HourView(true)

        setNotifButton.setOnClickListener {
            val curDateTime = Calendar.getInstance().timeInMillis
            datePicker.minDate = curDateTime
            var chosenDateTime = Calendar.getInstance()
            chosenDateTime.set(datePicker.year, datePicker.month, datePicker.dayOfMonth, timePicker.hour, timePicker.minute)

            Log.d(TAG, "curDateTime = ${curDateTime}")
            Log.d(TAG, "choosedDateTime = ${chosenDateTime.timeInMillis}")
            Log.d(TAG, "System.currentTimeMillis = ${System.currentTimeMillis()}")

            if(curDateTime<chosenDateTime.timeInMillis){
                addReminder(chosenDateTime.timeInMillis)
                dismiss()
            }
            else{
                Log.d(TAG, "Выбранная датавремя  меньше текущей")
            }
        }
    }

    private fun addReminder(dt:Long){
        Log.d(TAG, "setAlarm")
        val intent =  Intent("Reminder_${film.id}_$dt", null, context, AlarmReceiver::class.java)
            .putExtra(DETAILED_ID, "${film.id}")
            .putExtra(DETAILED_FILM_NAME, "${film.nameRu}")

        val pIntentOnce = PendingIntent.getBroadcast(App.instance.applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        am?.set(AlarmManager.RTC_WAKEUP, dt, pIntentOnce)
    }
}