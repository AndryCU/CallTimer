package com.andrydev.calltimer.view.fragments

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.adapters.PhonesAdapter
import com.andrydev.calltimer.databinding.FragmentCallsBinding
import com.andrydev.calltimer.model.entities.NumberEntity
import com.andrydev.calltimer.viewmodel.NumberViewModel

class CallsFragment : Fragment() {
    private var _binding:FragmentCallsBinding?=null
    private val binding get() = _binding!!

    private val numberViewModel: NumberViewModel by activityViewModels()

    lateinit var name:String
    lateinit var number:String

    val responseActivytiContact=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode== Activity.RESULT_OK){
            val contactUri: Uri? = it.data!!.data
            val cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val cursor: Cursor? = requireContext().contentResolver.query(
                contactUri!!, cols,
                null, null, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                name=cursor.getString(1)
                number = cursor.getString(0)
            }
            numberViewModel.insertNumber(NumberEntity(number = number, contactName = name))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCallsBinding.inflate(inflater,container,false)
        return binding.root
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNumberFAB.setOnClickListener {
            val i= Intent(Intent.ACTION_PICK)
            i.type= ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            responseActivytiContact.launch(i)
        }

        numberViewModel.numbers().observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                val adapter=PhonesAdapter(requireContext(),it)
                binding.listPhones.adapter=adapter
            }
        }

    }
}