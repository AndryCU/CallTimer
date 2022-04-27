package com.andrydev.calltimer.service.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import com.andrydev.calltimer.service.ForegroundService

class StartAlarm: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val audiomanager= context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audiomanager.ringerMode=AudioManager.RINGER_MODE_VIBRATE
        val i= Intent(context, ForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i)
        }
        else{
            context.startService(i)
        }
    }
}