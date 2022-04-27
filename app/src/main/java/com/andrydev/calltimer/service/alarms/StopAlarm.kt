package com.andrydev.calltimer.service.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.service.ForegroundService

class StopAlarm: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val audiomanager= context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audiomanager.ringerMode= AudioManager.RINGER_MODE_NORMAL
        val i=Intent(context,ForegroundService::class.java)
        val preferences=Preferences(context)
        preferences.saveAlarmStarted(false)
        context.stopService(i)
    }
}