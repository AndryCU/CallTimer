package com.andrydev.calltimer

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class CustomNotification(private val base: Context) : ContextWrapper(base)  {
    val CHANNEL_ID="com.andrydev.calltimer"

    init{
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            createNotificationChannel()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(){
        val name="CallTimer"
        val importance=NotificationManager.IMPORTANCE_DEFAULT
        val channel=NotificationChannel(CHANNEL_ID,name,importance).apply {
            description="description"
        }
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

   fun showNotification(title:String,message:String){
       val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
       val notification = NotificationCompat.Builder(this,CHANNEL_ID)
           .setContentTitle(title)
           .setContentText(message)
           .setSmallIcon(R.drawable.moon)
           .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
           .build()
       notificationManager.notify(1234,notification)
   }

}