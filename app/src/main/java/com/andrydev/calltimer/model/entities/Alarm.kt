package com.andrydev.calltimer.model.entities

import android.os.Build
import java.time.Instant
import java.time.ZoneOffset


class Alarm(
    var id:Int,
    var initmilliseconds:Long,
    var endmilliseconds:Long,
    var isActivated:Boolean
)


