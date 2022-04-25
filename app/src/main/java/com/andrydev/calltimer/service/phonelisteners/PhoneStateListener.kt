package com.andrydev.calltimer.service.phonelisteners

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.andrydev.calltimer.di.DBInterfaceHilt
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhoneStateListenerClass @Inject constructor(
    @ApplicationContext context: Context
): PhoneStateListener() {
    val entryPoint=EntryPointAccessors.fromApplication(context,DBInterfaceHilt::class.java).getDBCallTimer()


    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        Log.d("PhoneStateListener", "onCallStateChanged")
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("PhoneStateListener", "test repository ${entryPoint.getNumbersDao().getNumbers().size}")
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
}