package com.andrydev.calltimer

import com.andrydev.calltimer.model.entities.AlarmEntity
import java.time.*
import javax.inject.Inject

class Utiles @Inject constructor() {
    /*
        In this method i calc the time to start the alarm, baset in this idea:
        if the time selected is before current time, i have to add 1 day to the current date
     */
    fun calcInitTime(hour: Int, minute: Int): Long {
            var init =
                LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0).atZone(ZoneId.systemDefault())
            val now = LocalDateTime.now().withSecond(0).withNano(0).atZone(ZoneId.systemDefault())
            if (init.isBefore(now)) {
                init = init.plusDays(1)
            }
            return init.toInstant().toEpochMilli()
    }

    //this method return the time in milliseconds to start the alarm
    fun calcEndTime(hour: Int, minute: Int, initinmilliseconds: Long): Long {
            var endtime =
                LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0).atZone(ZoneId.systemDefault())
            val now = LocalDateTime.now().withSecond(0).withNano(0)
            val inittime =
                Instant.ofEpochMilli(initinmilliseconds).atZone(ZoneId.systemDefault())
            val days = inittime.dayOfMonth - now.dayOfMonth

            if (days == 1) {
                endtime = endtime.plusDays(1)
                if (endtime.isBefore(inittime)) {
                    endtime = endtime.plusDays(1)
                }
            }
            if (days == 0) {
                if (endtime.isBefore(inittime)) {
                    endtime = endtime.plusDays(1)
                }
            }
            return endtime.toInstant().toEpochMilli()

    }

    fun setAMPM(a: AlarmEntity, start: Boolean): String {
            val inittime: LocalDateTime = if (start) {
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            } else {
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
        return if (inittime.hour >= 12) {
            "PM"
        } else {
            "AM"
        }
    }

    fun hourwithminutetext(a: AlarmEntity, start: Boolean): String {
            val inittime: LocalDateTime = if (start) {
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            } else {
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
            var minute = ""
            minute = if (inittime.minute < 10) {
                "0${inittime.minute}"
            } else {
                "${inittime.minute}"
            }
        return if (inittime.hour >= 12) {
            "${inittime.hour - 12}:$minute"
        } else {
            "${inittime.hour}:$minute"
        }

    }


    fun iconAMPM(a: AlarmEntity, b: Boolean): Int {
            val inittime: LocalDateTime = if (b) {
                Instant.ofEpochMilli(a.initmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            } else {
                Instant.ofEpochMilli(a.endmilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
        return if (inittime.hour >= 19 || inittime.hour < 7) {
            R.drawable.moon
        } else {
            R.drawable.sun
        }
    }
}