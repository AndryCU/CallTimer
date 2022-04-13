package com.andrydev.calltimer.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "messages_table")
data class MessageEntity(
    @ColumnInfo(name = "destinynumber") val destinynumber:String,
    @ColumnInfo(name = "contact_name") val contact_name:String,
    @ColumnInfo(name = "message") val message:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int=0,
):Serializable
