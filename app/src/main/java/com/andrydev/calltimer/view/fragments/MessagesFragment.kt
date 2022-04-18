package com.andrydev.calltimer.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.adapters.MessagesAdapter

import com.andrydev.calltimer.databinding.FragmentMessagesBinding
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.view.dialogs.DialogSMS
import com.andrydev.calltimer.viewmodel.MessageViewModel
import dagger.hilt.EntryPoint
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
            dialogSMS.show(parentFragmentManager,"CustomDialog")
        }
        messageViewModel.messages().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                val adapter=MessagesAdapter(requireContext(),it)
                binding.listMessages.adapter=adapter
            }
        }
    }

}