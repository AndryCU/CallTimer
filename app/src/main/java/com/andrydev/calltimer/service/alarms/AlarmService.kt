package com.andrydev.calltimer.service.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.andrydev.calltimer.service.ForegroundService
import java.time.Instant
import java.time.ZoneId


class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    @SuppressLint("UnspecifiedImmutableFlag")
    fun setAlarm(timeInMillis:Long, id:Int, intent: Intent){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
           val a =Instant.ofEpochMilli(timeInMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                a.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                PendingIntent.getBroadcast(
                    context,
                    id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }else{
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                PendingIntent.getBroadcast(
                    context,
                    id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }

    }


    @SuppressLint("UnspecifiedImmutableFlag")
    fun cancelAlarm(id:Int, intent: Intent){
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
        context.stopService(Intent(context,ForegroundService::class.java))
    }

}