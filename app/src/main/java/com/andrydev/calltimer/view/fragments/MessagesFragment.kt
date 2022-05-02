package com.andrydev.calltimer.view.fragments

import android.Manifest.permission
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.adapters.MessagesAdapter
import com.andrydev.calltimer.databinding.FragmentMessagesBinding
import com.andrydev.calltimer.view.dialogs.DialogSMS
import com.andrydev.calltimer.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MessagesFragment : Fragment() {

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
            activityResultLauncher.launch(permission.SEND_SMS)
        }
        messageViewModel.messages().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                val adapter=MessagesAdapter(requireContext(),it)
                binding.listMessages.adapter=adapter
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                dialogSMS.show(parentFragmentManager,"CustomDialog")
            } else {
                val showRationale = shouldShowRequestPermissionRationale(permission.SEND_SMS)
                if (!showRationale){
                    val b= AlertDialog.Builder(requireContext())
                        .setTitle("Permiso no concedido")
                    b.setMessage("Necesitamos este permiso para enviar de forma automática los SMS que configura." +
                            " No leemos los SMS que tiene guardado ni enviamos ningún tipo de información.")
                    b.setPositiveButton("Conceder"){_,_->
                        val i= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri= Uri.fromParts("package", activity?.packageName,null)
                        i.data=uri
                        startActivity(i)
                    }
                    b.setNegativeButton("Denegar"){_,_->
                    }
                    b.show()
                }
            }
        }
}

