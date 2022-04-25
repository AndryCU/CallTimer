package com.andrydev.calltimer.service.phonelisteners

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateListenerClass (context: Context): PhoneStateListener() {
    val telephonyManager: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        Log.d("PhoneStateListener", "onCallStateChanged")
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("PhoneStateListener", "CALL_STATE_IDLE")
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("PhoneStateListener", "CALL_STATE_OFFHOOK")
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("PhoneStateListener", "CALL_STATE_RINGING with this number $incomingNumber")
            }
        }
        super.onCallStateChanged(state, incomingNumber)
    }
} // end class PhoneStateListene