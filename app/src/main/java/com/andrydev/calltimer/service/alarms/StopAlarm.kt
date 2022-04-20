package com.andrydev.calltimer.service.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.andrydev.calltimer.service.ForegroundService

class StopAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ZXCV","onRecieve StopAlarm")
        val i=Intent(context,ForegroundService::class.java)
        context.stopService(i)
    }
}