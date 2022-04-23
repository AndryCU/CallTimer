package com.andrydev.calltimer

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor (@ApplicationContext context: Context) {
    val SHARED_NAME="prefs"
    val INIT_APP="init_app"
    val ALARM_STARTED="alarm_started"
    val preferences=context.getSharedPreferences(SHARED_NAME,0)

    fun saveInit(v:Boolean){
        preferences.edit().putBoolean(INIT_APP,v).apply()
    }

    fun saveAlarmStarted(v:Boolean){
        preferences.edit().putBoolean(ALARM_STARTED,v).apply()
    }
    fun getAlarmStarted():Boolean{
        return preferences.getBoolean(ALARM_STARTED,false)
    }

    fun getsaveInit():Boolean{
        return preferences.getBoolean(INIT_APP,false)
    }
}