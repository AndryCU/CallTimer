package com.andrydev.calltimer.view.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.andrydev.calltimer.adapters.PhonesAdapter
import com.andrydev.calltimer.databinding.FragmentCallsBinding
import com.andrydev.calltimer.model.entities.NumberEntity
import com.andrydev.calltimer.viewmodel.NumberViewModel

class CallsFragment : Fragment() {
    private var _binding: FragmentCallsBinding?=null
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
                number = cursor.getString(0).replace(" ","").replace("(","").replace(")","").replace("-","")
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
            activityResultLauncher.launch(Manifest.permission.READ_PHONE_STATE)
        }
        numberViewModel.numbers().observe(viewLifecycleOwner){ listN->
            if (listN.isNotEmpty()){
                val adapter=PhonesAdapter(requireContext(),listN)
                binding.listPhones.adapter=adapter
                adapter.setItemClickListener(object : PhonesAdapter.ItemClicker {
                    override fun onDeleteClick(position: Int) {
                        Toast.makeText(requireContext(), "Borrado el contacto: ${listN[position].contactName}", Toast.LENGTH_SHORT).show()
                        numberViewModel.deletenumber(listN[position])
                    }
                })
            }else{
                val adapter=PhonesAdapter(requireContext(),listN)
                binding.listPhones.adapter=adapter
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                val i= Intent(Intent.ACTION_PICK)
                i.type= ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                responseActivytiContact.launch(i)
            } else {
                val showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)
                if (!showRationale){
                    val b= AlertDialog.Builder(requireContext())
                        .setTitle("Permiso no concedido")
                    b.setMessage("Necesitamos este permiso para saber el número que está llamando." +
                            " Sin este permiso no podrás añadir números a la lista de excepción. ")
                    b.setPositiveButton("Conceder"){_,_->
                        val i= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri= Uri.fromParts("package", activity?.packageName,null)
                        i.data=uri
                        startActivity(i)
                    }
                    b.setNegativeButton("Cancelar"){_,_->
                    }
                    b.show()
                }
            }
        }
}