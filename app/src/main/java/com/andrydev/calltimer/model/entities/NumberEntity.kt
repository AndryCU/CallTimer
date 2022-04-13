package com.andrydev.calltimer.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "number_table")
data class NumberEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int=0,
    @ColumnInfo(name = "number") val number:String,
    @ColumnInfo(name = "contactName") val contactName:String,
    @ColumnInfo(name = "isException") val isException:Boolean
    )
