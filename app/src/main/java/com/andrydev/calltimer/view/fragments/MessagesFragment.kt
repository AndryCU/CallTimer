package com.andrydev.calltimer.view.fragments

import android.Manifest.permission.*
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.adapters.MessagesAdapter
import com.andrydev.calltimer.databinding.FragmentMessagesBinding
import com.andrydev.calltimer.view.dialogs.DialogSMS
import com.andrydev.calltimer.viewmodel.MessageViewModel
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MessagesFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    @Inject lateinit var dialogSMS: DialogSMS
    private var _binding:FragmentMessagesBinding?=null
    private val binding get() = _binding!!
    private val messageViewModel: MessageViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding= FragmentMessagesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddMessage.setOnClickListener {
           if (EasyPermissions.hasPermissions(requireContext(),SEND_SMS, READ_PHONE_STATE)){
               dialogSMS.show(parentFragmentManager,"CustomDialog")
           }else{
               requestPX()
           }
        }
        messageViewModel.messages().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                val adapter=MessagesAdapter(requireContext(),it)
                binding.listMessages.adapter=adapter
            }
        }
    }


    fun requestPX(){
        EasyPermissions.requestPermissions(
            this,
            "Necesitamos el acceso a los SMS para" +
                "Uno de estos perimosos no esta garantizado", 1 , SEND_SMS, READ_PHONE_STATE)

        }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            val b= AlertDialog.Builder(requireContext())
                .setTitle("Permiso no concedido")
            b.setMessage("Para usar los mensajes automáticos debes dar permiso en la siguiente pantalla al acceso a los " +
                    "SMS y a las llamadas.")
            b.setPositiveButton("Conceder"){_,_->
                val i= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri= Uri.fromParts("package", activity?.packageName,null)
                i.data=uri
                startActivity(i)
            }
            b.setNegativeButton("Cancelar"){_,_->
            }
            b.show()
        }else{
            requestPX()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}