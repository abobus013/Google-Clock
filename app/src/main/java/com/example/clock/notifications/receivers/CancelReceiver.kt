package com.example.clock.notifications.receivers

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class CancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TTTT", "Received cancel intent")

//            val intent = Intent(context, AlarmReceiver::class.java)
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
//            )
//
//            val alarmManager =
//                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.cancel(
//                pendingIntent
//            )
//            val intent = Intent(context, AlarmReceiver::class.java)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = intent.getIntExtra("notification_id", -1)
            Log.d("CancelReceiver", "Notification ID: $notificationId")
            notificationManager.cancel("TTTT", notificationId)
            Log.d("TTTT","IT IS CANCEL ID: $notificationId")

    }
}





