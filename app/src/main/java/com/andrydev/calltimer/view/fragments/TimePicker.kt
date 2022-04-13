package com.andrydev.calltimer.view.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePicker(val listener:(hour:Int,minute:Int)->Unit):DialogFragment(),TimePickerDialog.OnTimeSetListener {

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener(hourOfDay,minute)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar=Calendar.getInstance()
        return TimePickerDialog(activity as Context, this, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

    }
}