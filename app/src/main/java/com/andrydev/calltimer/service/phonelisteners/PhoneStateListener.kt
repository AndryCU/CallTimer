package com.andrydev.calltimer.service.phonelisteners

import android.content.Context
import android.telephony.PhoneNumberUtils
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

        if(state==TelephonyManager.CALL_STATE_RINGING &&checkIfPhoneExist(incomingNumber)){
            Log.d("ZXCV","numero existe")
        }
        if (state==TelephonyManager.CALL_STATE_IDLE){
            Log.d("ZXCV","CALL_STATE_IDLE")
        }
        super.onCallStateChanged(state, incomingNumber)
    }

    fun checkIfPhoneExist(incomingNumber: String):Boolean{
        val listPhoneNumbers=entryPoint.getNumbersDao().getNumbers()
        for (phone in listPhoneNumbers){
            if (incomingNumber.contains(phone.number,true)){
                return true
            }
        }
        return false
    }
}