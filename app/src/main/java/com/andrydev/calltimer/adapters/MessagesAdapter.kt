package com.andrydev.calltimer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.andrydev.calltimer.R
import com.andrydev.calltimer.model.entities.MessageEntity

class MessagesAdapter (private var contex:Context, private val messages:List<MessageEntity>) : ArrayAdapter<MessageEntity> (contex,0,messages){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v=LayoutInflater.from(context).inflate(R.layout.item_message,parent,false)
        val messages=messages[position]
        v.findViewById<TextView>(R.id.contact_text_item).text=messages.contact_name
        v.findViewById<TextView>(R.id.message_text_item).text=messages.message
        v.findViewById<TextView>(R.id.textviewContactNumber).text=messages.destinynumber
        return v
    }

}