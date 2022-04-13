package com.andrydev.calltimer.viewmodel.repository

import com.andrydev.calltimer.model.daos.AlarmDao
import com.andrydev.calltimer.model.entities.AlarmEntity
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {
    suspend fun insertAlarm(alarm: AlarmEntity){
        alarmDao.insertDao(alarm)
    }

    suspend fun getAlarm()=alarmDao.getAlarmDao()

    suspend fun updateInitRepository(i:Long){
        alarmDao.updateInitTimeDao(i)
    }

    suspend fun updateEndRepository(i:Long){
        alarmDao.updateEndTimeDao(i)
    }

    suspend fun updateActivateRepository(i:Boolean){
        alarmDao.updateActivateDao(i)
    }
}