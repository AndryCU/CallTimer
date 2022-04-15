package com.andrydev.calltimer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrydev.calltimer.model.entities.AlarmEntity
import com.andrydev.calltimer.viewmodel.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val repository: AlarmRepository,
) :ViewModel() {
    private val _initTime = MutableLiveData<Long>()
    private val _endTime = MutableLiveData<Long>()
    private val _activated = MutableLiveData<Boolean>()

    fun alarmLiveDataInit():LiveData<Long>{
        return _initTime
    }

    fun alarmLiveDataEnd():LiveData<Long>{
        return _endTime
    }

    fun alarmLiveDataActivated():LiveData<Boolean>{
        return _activated
    }

    fun addAlarmC(alarma: AlarmEntity) {
       runBlocking {
                repository.insertAlarm(alarma)
            }
    }

    fun updateAlarmInit(i: Long) {
            runBlocking {
                repository.updateInitRepository(i)
            }
        _initTime.value=i
    }

    fun updateAlarmEnd(i: Long) {
        runBlocking{
           repository.updateEndRepository(i)
        }
        _endTime.value=i
    }

     fun checkAlarmOK():Boolean{
         var boolean: Boolean
        runBlocking {
            boolean= repository.getAlarm().initmilliseconds != 0L && repository.getAlarm().endmilliseconds != 0L
        }
        return boolean
    }

    fun updateAlarmActivate(i: Boolean) {
               runBlocking {
                            repository.updateActivateRepository(i)
                    }

        _activated.value = i
    }

    fun getAlarm():AlarmEntity {
        var alarma: AlarmEntity?
        runBlocking {
            alarma=  repository.getAlarm()
        }
        return alarma!!
    }
}
