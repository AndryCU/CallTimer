package com.andrydev.calltimer.model.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andrydev.calltimer.model.entities.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages_table ORDER BY id ASC")
    fun getMessages(): LiveData<List<MessageEntity>>

    @Query("SELECT * FROM messages_table ORDER BY id ASC")
    fun getMessagesForSMS(): List<MessageEntity>

    @Query ("SELECT * FROM messages_table WHERE id=:idmensage ")
    fun get(idmensage:Int):LiveData<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDao(messageEntity: MessageEntity)

    @Update
    suspend fun updateDao(messageEntity: MessageEntity)

    @Delete
    suspend fun deleteDao(messageEntity: MessageEntity)

}