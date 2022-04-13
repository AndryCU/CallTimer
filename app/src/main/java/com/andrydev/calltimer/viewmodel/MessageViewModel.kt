package com.andrydev.calltimer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrydev.calltimer.model.entities.AlarmEntity
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.viewmodel.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository
):ViewModel() {
    private val _messages=MutableLiveData<List<MessageEntity>>()

    //fun repository()=repository

    fun messages():LiveData<List<MessageEntity>>{
        return repository.getMessages()
    }

    fun getMessages(){
        repository.getMessages()
    }


    fun insertMessage(messageEntity: MessageEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO)  {
                repository.insertMessage(messageEntity)
            }
        }

    }


}