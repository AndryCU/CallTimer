package com.andrydev.calltimer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat

class ForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification=NotificationService(this).showNotification("Service","Test Notification")
        startForeground(1234,notification)
        Log.d("ZXCV","inicia el servivio")
        return START_STICKY
    }
}