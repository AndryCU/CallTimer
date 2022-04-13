package com.andrydev.calltimer.viewmodel.repository

import com.andrydev.calltimer.model.daos.MessageDao
import com.andrydev.calltimer.model.entities.MessageEntity
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messagedao: MessageDao
) {
    suspend fun insertMessage(messageEntity: MessageEntity)=messagedao.insertDao(messageEntity)
    fun getMessages()=messagedao.getMessages()
    suspend fun updateMessage(messageEntity: MessageEntity)=messagedao.updateDao(messageEntity)
    suspend fun deleteMessage(messageEntity: MessageEntity)=messagedao.deleteDao(messageEntity)
}