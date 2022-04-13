package com.andrydev.calltimer.model.daos

import androidx.room.*
import com.andrydev.calltimer.model.entities.AlarmEntity

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_table")
    suspend fun getAlarmDao(): AlarmEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDao(alarmEntity: AlarmEntity)

    @Query("update alarm_table set initmilliseconds=:inittime where id=0")
    suspend fun updateInitTimeDao(inittime:Long)

    @Query("update alarm_table set endmilliseconds=:endtime where id=0")
    suspend fun updateEndTimeDao(endtime:Long)

    @Query("update alarm_table set isActivated=:activate where id=0")
    suspend fun updateActivateDao(activate:Boolean)
}