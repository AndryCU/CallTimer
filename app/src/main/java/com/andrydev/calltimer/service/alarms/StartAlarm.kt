package com.andrydev.calltimer.service.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.andrydev.calltimer.service.ForegroundService

class StartAlarm: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i= Intent(context, ForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("ZXCV","onReceive BroadcastReceiver")
            context?.startForegroundService(i)
        }
        else{
            context?.startService(i)
        }
    }
}