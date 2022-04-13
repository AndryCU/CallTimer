package com.andrydev.calltimer.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey()
    @ColumnInfo( name = "id") val id:Int=0,
    @ColumnInfo(name = "initmilliseconds") val initmilliseconds:Long,
    @ColumnInfo(name = "endmilliseconds") val endmilliseconds:Long,
    @ColumnInfo( name = "isActivated") val isActivated:Boolean
    )
fun Alarm.toDatabase()=AlarmEntity(id = id, initmilliseconds = initmilliseconds, endmilliseconds = endmilliseconds ,isActivated = isActivated)