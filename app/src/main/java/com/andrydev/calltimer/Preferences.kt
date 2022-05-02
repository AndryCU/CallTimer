package com.andrydev.calltimer

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor (@ApplicationContext context: Context) {
    private val SHARED_NAME="prefs"
    private val INIT_APP="init_app"
    private val INIT_APP_INTRODUCTION_SCREEN="init_app_introduction_screen"
    private val ALARM_STARTED="alarm_started"
    private val RINGTONE_MODE="ringtone_mode"
    private val preferences: SharedPreferences =context.getSharedPreferences(SHARED_NAME,0)

    fun saveInitIntroductionScreen(v:Boolean){
        preferences.edit().putBoolean(INIT_APP_INTRODUCTION_SCREEN,v).apply()
    }

    fun saveInit(v:Boolean){
        preferences.edit().putBoolean(INIT_APP,v).apply()
    }

    fun saveAlarmStarted(v:Boolean){
        preferences.edit().putBoolean(ALARM_STARTED,v).apply()
    }

    fun saveRingToneMode(mode: Int){
        preferences.edit().putInt(RINGTONE_MODE,mode).apply()
    }

    fun getAlarmStarted():Boolean{
        return preferences.getBoolean(ALARM_STARTED,false)
    }

    fun getsaveInit():Boolean{
        return preferences.getBoolean(INIT_APP,false)
    }

    fun getRingToneMode():Int{
        return preferences.getInt(RINGTONE_MODE,1)
    }

    fun getInitIntroductionScreen():Boolean{
        return preferences.getBoolean(INIT_APP_INTRODUCTION_SCREEN,false)
    }

}