package com.andrydev.calltimer.viewmodel.repository

import com.andrydev.calltimer.model.daos.NumberDao
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.model.entities.NumberEntity
import javax.inject.Inject

class NumberRepository @Inject constructor( private val numberDao: NumberDao) {
    suspend fun insertNumber(numberEntity: NumberEntity)=numberDao.insertDao(numberEntity)
    fun getNumbersLiveData()=numberDao.getNumbersLiveData()
    suspend fun deleteNumber(numberEntity: NumberEntity)=numberDao.deleteDao(numberEntity)
    fun getNummbers()=numberDao.getNumbers()
}