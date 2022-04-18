package com.andrydev.calltimer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.model.entities.NumberEntity
import com.andrydev.calltimer.viewmodel.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NumberViewModel @Inject constructor( private val repository: NumberRepository):ViewModel() {

    fun numbers():LiveData<List<NumberEntity>>{
        return repository.getNumbersLiveData()
    }

    fun insertNumber(numberEntity: NumberEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertNumber(numberEntity)
            }
        }
    }

}