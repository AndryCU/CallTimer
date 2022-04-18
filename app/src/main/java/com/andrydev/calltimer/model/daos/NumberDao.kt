package com.andrydev.calltimer.model.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.model.entities.NumberEntity

@Dao
interface NumberDao {
    @Query("SELECT * FROM number_table ORDER BY id ASC")
    fun getNumbersLiveData(): LiveData<List<NumberEntity>>

    @Query("SELECT * FROM number_table ORDER BY id ASC")
    fun getNumbers(): List<NumberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDao(numberEntity: NumberEntity)

    @Delete
    suspend fun deleteDao(numberEntity: NumberEntity)
}