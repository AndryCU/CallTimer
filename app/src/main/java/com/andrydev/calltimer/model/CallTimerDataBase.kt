package com.andrydev.calltimer.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andrydev.calltimer.model.daos.AlarmDao
import com.andrydev.calltimer.model.daos.MessageDao
import com.andrydev.calltimer.model.daos.NumberDao
import com.andrydev.calltimer.model.entities.AlarmEntity
import com.andrydev.calltimer.model.entities.MessageEntity


@Database(
    entities = [AlarmEntity::class, MessageEntity::class],
    version = 1
)
abstract class CallTimerDataBase: RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao
    abstract fun getMessagesDao(): MessageDao
    abstract fun getNumbersDao(): NumberDao
}