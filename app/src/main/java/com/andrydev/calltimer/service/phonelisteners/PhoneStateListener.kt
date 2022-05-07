package com.andrydev.calltimer.service.phonelisteners

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.di.DBInterfaceHilt
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhoneStateListenerClass @Inject constructor(
    @ApplicationContext private val context: Context,
): PhoneStateListener() {
    val defaultRingtoneUri: Uri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_RINGTONE)
    val entryPoint=EntryPointAccessors.fromApplication(context,DBInterfaceHilt::class.java).getDBCallTimer()
    val mp:MediaPlayer= MediaPlayer.create(context,defaultRingtoneUri)
    val preferences=Preferences(context)

    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        mp.isLooping=true
        if(state==TelephonyManager.CALL_STATE_RINGING && checkIfPhoneExist(incomingNumber)){
            //i know the best way is set ring tone mode to normal, but when i set it, the phone don't sound
            mp.start()
        }
        if (state==TelephonyManager.CALL_STATE_IDLE){
                mp.pause()
                mp.seekTo(0)
            checkIfPhoneExistForSMS(incomingNumber)
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

    fun checkIfPhoneExistForSMS(incomingNumber: String){
        val listPhoneNumbers=entryPoint.getMessagesDao().getMessagesForSMS()
        for (phone in listPhoneNumbers){
            if (incomingNumber.contains(phone.destinynumber,true)){
                    //i know that this method is decrepated but i could't find the rigth way for send a SMS
                    val smsManager= SmsManager.getDefault()
                    smsManager.sendTextMessage(incomingNumber,null,phone.message,null,null)
            }
        }
    }
}