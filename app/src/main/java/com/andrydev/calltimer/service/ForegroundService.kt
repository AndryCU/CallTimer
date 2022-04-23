package com.andrydev.calltimer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ForegroundService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification=NotificationService(this).showNotification("Service","Test Notification")
        startForeground(1234,notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}