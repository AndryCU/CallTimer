package com.andrydev.calltimer.view.dialogs

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.andrydev.calltimer.R
import com.andrydev.calltimer.model.entities.MessageEntity
import com.andrydev.calltimer.viewmodel.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DialogSMS @Inject constructor(
    private val messageRepository: MessageRepository
): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.border)
        val view:View=inflater.inflate(R.layout.dialog_sms_view,container,false)

        lateinit var name:String
        lateinit var number:String

        val responseActivytiContact=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                val contactUri: Uri? = it.data!!.data
                val cols = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val cursor: Cursor? = requireContext().contentResolver.query(
                    contactUri!!, cols,
                    null, null, null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    name=cursor.getString(1)
                    number = cursor.getString(0)
                    view.findViewById<TextView>(R.id.textviewContact).text=name
                }
                cursor!!.close()
            }
        }

        view.findViewById<ImageButton>(R.id.imageButtonAddContact).setOnClickListener {
            val i=Intent(Intent.ACTION_PICK)
            i.type= ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            responseActivytiContact.launch(i)
        }
        view.findViewById<TextView>(R.id.saveButtomDialog).setOnClickListener {
            val messagetext=view.findViewById<EditText>(R.id.editTextSMSDialog).text.toString()
            if (messagetext.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    messageRepository.insertMessage(MessageEntity(number, name,messagetext))
                }
                dismiss()
            }else{
                Toast.makeText(context,"Introdusca un texto para el mensaje o un número de telefono para enviar el SMS",Toast.LENGTH_LONG).show()
            }
        }

        view.findViewById<TextView>(R.id.cancelButtonDialog).setOnClickListener {
            dismiss()
        }

        return view
    }




    override fun onStart() {
        super.onStart()
        val width=(resources.displayMetrics.widthPixels*0.85).toInt()
        val heith=(resources.displayMetrics.heightPixels*0.45).toInt()
        dialog?.window?.setLayout(width,heith)
    }
}