package com.andrydev.calltimer

import android.os.Build
import android.util.Log
import com.andrydev.calltimer.model.entities.AlarmEntity
import java.time.*
import java.util.*
import javax.inject.Inject

class Utiles @Inject constructor(){
    /*
        In this method i calc the time to start the alarm, baset in this idea:
        if the time selected is before current time, i have to add 1 day to the current date
     */
    fun calcInitTime(hour:Int,minute:Int):Long{
        //FOR ANDROID VERSIONS ABOVE OF OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var init=LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0)
            val now=LocalDateTime.now().withSecond(0).withNano(0)
            if (init.isBefore(now)){
                init=init.plusDays(1)
            }

            Log.d("ZXCV"," inicio time${init}")
            return init.toInstant(ZoneOffset.UTC).toEpochMilli()
        } else {////FOR ANDROID VERSIONS PRIOR OF OREO
            val timenow = Calendar.getInstance()
            timenow.set(Calendar.SECOND, 0)
            timenow.set(Calendar.MILLISECOND, 0)

            val timeuserinit = Calendar.getInstance()
            timeuserinit.set(Calendar.HOUR, hour)
            timeuserinit.set(Calendar.MINUTE, minute)
            timeuserinit.set(Calendar.MILLISECOND, 0)
            timeuserinit.set(Calendar.SECOND, 0)


            if (timeuserinit.before(timenow)) {
                timeuserinit.add(Calendar.DAY_OF_MONTH, 1)
            }
            return timeuserinit.timeInMillis
        }
    }
    //this method return the time in milliseconds to start the alarm
    fun calcEndTime(hour: Int, minute: Int, initinmilliseconds:Long):Long {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var endtime=LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0)
            val now = LocalDateTime.now().withSecond(0).withNano(0)
            val inittime = Instant.ofEpochMilli(initinmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            val days=inittime.dayOfMonth-now.dayOfMonth

            if(days==1){
                endtime=endtime.plusDays(1)
                if (endtime.isBefore(inittime)){
                    endtime=endtime.plusDays(1)
                }
            }
            if (days==0){
                if (endtime.isBefore(inittime)){
                    endtime=endtime.plusDays(1)
                }
            }
            return endtime.toInstant(ZoneOffset.UTC).toEpochMilli()

        } else {

            val timeuserend = Calendar.getInstance()
            timeuserend.set(Calendar.HOUR, hour)
            timeuserend.set(Calendar.MINUTE, minute)
            timeuserend.set(Calendar.MILLISECOND, 0)
            timeuserend.set(Calendar.SECOND, 0)
            val calendarinit = Calendar.getInstance()
            calendarinit.timeInMillis = initinmilliseconds
            val days =
                calendarinit.get(Calendar.DAY_OF_MONTH) - Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            if (days == 1 ) {
                timeuserend.add(Calendar.DAY_OF_MONTH, 1)
                if (timeuserend.before(calendarinit)){
                    timeuserend.add(Calendar.DAY_OF_MONTH,1)
                }
            }
            if (days==0) {
                if (timeuserend.before(calendarinit)){
                    timeuserend.add(Calendar.DAY_OF_MONTH,1)
                }
            }

            return timeuserend.timeInMillis
        }
    }

    fun setAMPM(a: AlarmEntity,start:Boolean):String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lateinit var inittime:LocalDateTime
            inittime = if (start){
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }else{
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }
            if (inittime.hour>=12){
                "PM"
            }else{
                "AM"
            }
        }else{
            //TODO hacer este
            ""
        }
    }

    fun hourwithminutetext(a: AlarmEntity,start: Boolean):String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lateinit var inittime:LocalDateTime
            inittime = if (start){
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }else{
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }
            var minute=""
            minute = if (inittime.minute<10){
                "0${inittime.minute}"
            }else{
                "${inittime.minute}"
            }
            if (inittime.hour>=12){
                "${inittime.hour-12}:$minute"
            }else{
                "${inittime.hour}:$minute"
            }
        }else{
            ""
        }
    }

    fun iconAMPM(a: AlarmEntity,b:Boolean):Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var inittime:LocalDateTime = if (b){
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }else{
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneOffset.UTC).toLocalDateTime()
            }
            if (inittime.hour >= 19 || inittime.hour < 7) {
                R.drawable.moon
            } else {
                R.drawable.sun
            }
        }else{
            0
        }
    }

}