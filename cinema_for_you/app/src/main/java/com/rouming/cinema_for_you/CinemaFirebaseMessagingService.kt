package com.rouming.cinema_for_you

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rouming.cinema_for_you.MainActivity.Companion.TAG

class CinemaFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            // Handle message
            sendNotification(remoteMessage.data)
        }
    }

    private fun sendNotification(data:Map<String, String>){
        Log.d(TAG, "sendNotification FCM")

        val MainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        MainActivityIntent.putExtra(MainActivity.DETAILED_ID,data[MainActivity.DETAILED_ID])
        MainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(applicationContext, 789, MainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("It's time to see something interesting =)")
            .setContentText("You wanna to see ${if(data[MainActivity.DETAILED_FILM_NAME]== null) "a movie" else data[MainActivity.DETAILED_FILM_NAME]} right now")
            .setPriority(NotificationCompat.PRIORITY_LOW) // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(data[MainActivity.DETAILED_ID]!!.toInt(), builder.build())

    }



}