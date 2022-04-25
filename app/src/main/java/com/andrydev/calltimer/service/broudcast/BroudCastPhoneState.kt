package com.andrydev.calltimer.service.broudcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.andrydev.calltimer.service.phonelisteners.PhoneStateListenerClass

class BroudcastPhone : BroadcastReceiver() {
    lateinit var phoneStateListenerClass: PhoneStateListenerClass
    override fun onReceive(context: Context?, p1: Intent?) {
        val tfm = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListenerClass = PhoneStateListenerClass(context)
        tfm.listen(phoneStateListenerClass, PhoneStateListener.LISTEN_CALL_STATE)
    }
}