package com.rouming.cinema_for_you

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rouming.cinema_for_you.MainActivity.Companion.DETAILED_FILM_NAME
import com.rouming.cinema_for_you.MainActivity.Companion.DETAILED_ID
import com.rouming.cinema_for_you.MainActivity.Companion.TAG

class AlarmReceiver  : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        //TODO пересоздание алармов после рестарта (и возможно нотификаейшн ченела?)

        val MainActivityIntent = Intent(ctx, MainActivity::class.java)
        MainActivityIntent.putExtra(DETAILED_ID,intent.getStringExtra(DETAILED_ID))
        MainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(ctx, 789, MainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(ctx, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("It's time see something interesting =) ${intent.getStringExtra(DETAILED_ID)}")
            .setContentText("You want to see ${if(intent.getStringExtra(DETAILED_FILM_NAME)== null) "a movie" else intent.getStringExtra(DETAILED_FILM_NAME)}")
            .setPriority(NotificationCompat.PRIORITY_LOW) // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(ctx)
        notificationManager.notify(intent.getStringExtra(DETAILED_ID)!!.toInt(), builder.build())
        // notificationId is a unique int for each notification that you must define

    }
}