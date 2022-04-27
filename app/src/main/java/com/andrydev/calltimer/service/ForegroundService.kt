package com.andrydev.calltimer.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.andrydev.calltimer.service.phonelisteners.PhoneStateListenerClass

class ForegroundService: Service() {
    companion object{
        var recierver:BroudcastPhone?=null
    }

    class BroudcastPhone : BroadcastReceiver() {
        override fun onReceive(context: Context?, p1: Intent?) {
            val tfm = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            if (recierver==null){
                recierver=BroudcastPhone()
                tfm.listen(PhoneStateListenerClass(context), PhoneStateListener.LISTEN_CALL_STATE)
            }
        }
    }

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