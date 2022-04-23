package com.andrydev.calltimer.service.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.service.ForegroundService

class StopAlarm: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val i=Intent(context,ForegroundService::class.java)
        val preferences=Preferences(context)
        preferences.saveAlarmStarted(false)
        context.stopService(i)
    }
}