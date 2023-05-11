package com.example.clock.notifications.receivers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.clock.R

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notification = Notification.Builder(context, "My notification")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("The Notification")
                .setAutoCancel(true)
                .setContentText("It is simple description for testing notifications").build()

            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "My notification",
                    "вывести Alarm notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
            notificationManager.notify("TTTT", 0, notification)
        }
    }

}

