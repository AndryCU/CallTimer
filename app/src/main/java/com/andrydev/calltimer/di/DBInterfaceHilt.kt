package com.andrydev.calltimer.di

import com.andrydev.calltimer.model.CallTimerDataBase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DBInterfaceHilt {
    fun getDBCallTimer():CallTimerDataBase
}