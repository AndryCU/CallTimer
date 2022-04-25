package com.andrydev.calltimer.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.andrydev.calltimer.service.broudcast.BroudcastPhone

class ForegroundService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification=NotificationService(this).showNotification("Service","Test Notification")
        startForeground(1234,notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        val intentFilter= IntentFilter()
        intentFilter.addAction("android.intent.action.PHONE_STATE")
        val reciever= BroudcastPhone()
        registerReceiver(reciever,intentFilter)
    }
}