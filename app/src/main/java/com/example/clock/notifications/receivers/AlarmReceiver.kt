package com.example.clock.notifications.receivers


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.clock.R
import com.example.clock.notifications.AlarmActivity
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TTTT","OnReceiver")
        val fullScreenIntent = Intent(context, AlarmActivity::class.java)
        val fullScreenPendingIntent =
            PendingIntent.getActivity(context,0, fullScreenIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationId = Random.nextInt(0, 40000)

        Log.d("TTTT","THIS INIT ID: $notificationId")

        val cancelIntent = Intent(context, CancelReceiver::class.java)
        cancelIntent.action = "STOP_ACTION"
        cancelIntent.putExtra("notification_id", notificationId)

        val cancelPendingIntent = PendingIntent.getBroadcast(
            context, notificationId , cancelIntent ,  PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(context!!, "HELLO_ID")
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("будильник")
                .setContentText("пора просыпаться")
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setContentIntent(fullScreenPendingIntent)
                .setFullScreenIntent(fullScreenPendingIntent,true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .addAction(R.drawable.ic_launcher_foreground, "Отложить", cancelPendingIntent )
                .addAction(R.drawable.ic_launcher_background,"Выключить", cancelPendingIntent)
                .build()


        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(
            NotificationChannel(
                "HELLO_ID", "ALARM ACTION CHANNEL", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "CHANNEL_DESCRIPTION"
                enableVibration(true)
                enableLights(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
        )

        notificationManager.notify("TTTT", notificationId, notification)

    }

}
