package com.andrydev.calltimer.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.CustomNotification
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.R
import com.andrydev.calltimer.Utiles
import com.andrydev.calltimer.databinding.FragmentTimeBinding
import com.andrydev.calltimer.model.entities.Alarm
import com.andrydev.calltimer.model.entities.toDatabase
import com.andrydev.calltimer.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

class TimeFragment : Fragment() {
    lateinit var utiles: Utiles
    lateinit var preferences: Preferences
    private var _binding: FragmentTimeBinding? = null
    private val binding get() = _binding!!
    private val alarmViewModel: AlarmViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utiles= Utiles()
        preferences= Preferences(requireContext())
        if (preferences.getsaveInit()){
            setTimeInitText()
            setTimeEndText()
        }else{
            alarmViewModel.addAlarmC(Alarm(0,0,0,false).toDatabase())
            binding.textviewTimeStart.text="--:--"
            binding.textviewAMPMStart.text="AM"
            binding.imageviewstart.setImageResource(R.drawable.sun)
            binding.textviewTimeEnd.text="--:--"
            binding.textviewAMPMEnd.text="AM"
            binding.imageViewEnd.setImageResource(R.drawable.sun)
        }

        binding.layoutStart.setOnClickListener {
            showTimePickcerDialog(true)
        }
        binding.layoutEnd.setOnClickListener {
            showTimePickcerDialog(false)
        }

        binding.switchalarm.setChecked(alarmViewModel.getAlarm().isActivated)
        binding.switchalarm.setOnClickListener {
            if (binding.switchalarm.isChecked){
                alarmViewModel.updateAlarmActivate(false)
                binding.switchalarm.setChecked(false)
            }else{
                if (alarmViewModel.checkAlarmOK()){
                    binding.switchalarm.setChecked(true)
                    alarmViewModel.updateAlarmActivate(true)
                    preferences.saveInit(true)
                    CustomNotification(requireContext()).showNotification("CallTimer", "Alarm is set")
                }else{
                    Toast.makeText(context,"Hora de inicio o fin vacías",Toast.LENGTH_SHORT).show()
                }
            }
        }

       alarmViewModel.alarmLiveDataInit().observe(viewLifecycleOwner) {
            setTimeInitText()
        }
        alarmViewModel.alarmLiveDataEnd().observe(viewLifecycleOwner){
            setTimeEndText()
        }
    }

    private fun setTimeInitText() {
        val a = alarmViewModel.getAlarm()
        binding.textviewTimeStart.text = utiles.hourwithminutetext(a, true)
        binding.textviewAMPMStart.text = utiles.setAMPM(a, true)
        binding.imageviewstart.setImageResource(utiles.iconAMPM(a,true))
    }

    private fun setTimeEndText() {
        val a = alarmViewModel.getAlarm()
        binding.textviewTimeEnd.text = utiles.hourwithminutetext(a, false)
        binding.textviewAMPMEnd.text = utiles.setAMPM(a, false)
        binding.imageViewEnd.setImageResource(utiles.iconAMPM(a,false))
    }


    private fun showTimePickcerDialog(start:Boolean){
        if (start){
            val timePicker=TimePicker{
                    hour, minute ->
                        alarmViewModel.updateAlarmInit(utiles.calcInitTime(hour,minute))
            }
            timePicker.show(parentFragmentManager,"")
        }else{
            val timePicker=TimePicker{
                    hour, minute ->
                val alarm=alarmViewModel.getAlarm()
                        val initemp=Calendar.getInstance()
                        initemp.timeInMillis=alarm.initmilliseconds
                        alarmViewModel.updateAlarmEnd(utiles.calcEndTime(hour,minute,initemp.timeInMillis))
            }
            timePicker.show(parentFragmentManager,"")
        }
    }
}