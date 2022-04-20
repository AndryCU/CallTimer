package com.andrydev.calltimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.andrydev.calltimer.R

/*
    In this class i create the notification that i will use to setup the foregound service. It's mantatory to have a notification for
    the Foreground Services
 */

class NotificationService(private val base: Context) : ContextWrapper(base)  {
    val CHANNEL_ID="com.andrydev.calltimer"

    init{
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(){
        val name="CallTimer"
        val importance= NotificationManager.IMPORTANCE_DEFAULT
        val channel= NotificationChannel(CHANNEL_ID,name,importance).apply {
            description="description"
        }
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //return the notification. It's used in ForegroundService.kt
    fun showNotification(title:String,message:String):Notification{
        var contentview= RemoteViews(packageName,R.layout.notification_layout)
        contentview.setTextViewText(R.id.textview_notification_end,"esto es una prueba")
        contentview.setTextViewText(R.id.textview_notification_start,"esto es una prueba")
        return NotificationCompat.Builder(this,CHANNEL_ID)
            .setCustomContentView(contentview)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()
    }

}