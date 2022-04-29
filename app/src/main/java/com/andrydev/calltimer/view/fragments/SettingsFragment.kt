package com.andrydev.calltimer.view.fragments

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding:FragmentSettingsBinding?=null
    private val binding get()=_binding!!
    lateinit var preferences: Preferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences= Preferences(requireContext())
        binding.spinnermode.isChecked = preferences.getRingToneMode()==0
        binding.spinnermode.onFocusChangeListener=null
        binding.spinnermode.setOnCheckedChangeListener { button, _ ->
            val notimanager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notimanager.isNotificationPolicyAccessGranted){
                checkNotificationService()
                button.isChecked=false
            }else{
                if (button.isChecked){
                    preferences.saveRingToneMode(0)
                    button.isChecked=true
                }else{
                    preferences.saveRingToneMode(1)
                    button.isChecked=false
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNotificationService () {
        AlertDialog.Builder(requireContext())
            .setTitle("Solicitud de Permiso")
            .setMessage(
                "Necesitamos permiso para modificar los ajustes del telefono. Esto nos permite establecer el modo: NO MOLESTAR de manera automática." +
                        "Sin este permiso no prodrás establecer el modo silencio en la aplicación.")
            .setPositiveButton("Conceder") { _, _ ->
                val intent =
                    Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Denegar") { _, _ ->
            }
            .show()
    }
}