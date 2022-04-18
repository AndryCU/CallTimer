package com.andrydev.calltimer.di

import android.content.Context
import androidx.room.Room
import com.andrydev.calltimer.model.CallTimerDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    const val DATABASE_NAME="ctdb"

    @Singleton
    @Provides
    fun provideroom(@ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        CallTimerDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideAlarmDao(db:CallTimerDataBase)=db.getAlarmDao()

    @Singleton
    @Provides
    fun provideMessageDao(db: CallTimerDataBase)=db.getMessagesDao()

    @Singleton
    @Provides
    fun provideNumbersDao(db: CallTimerDataBase)=db.getNumbersDao()
}