package com.andrydev.calltimer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.andrydev.calltimer.R
import com.andrydev.calltimer.model.entities.NumberEntity


class PhonesAdapter (private var contex:Context, private val phones:List<NumberEntity>) : ArrayAdapter<NumberEntity> (contex,0,phones){
    private lateinit var mListener: ItemClicker
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v= LayoutInflater.from(context).inflate(R.layout.item_phones,parent,false)
        val phones=phones[position]
        v.findViewById<TextView>(R.id.contactName).text=phones.contactName
        v.findViewById<TextView>(R.id.phoneNumber).text=phones.number

        v.findViewById<ImageView>(R.id.imagedeletenumberlistview).setOnClickListener {
            mListener.onDeleteClick(position)
        }

        return v
    }

    interface ItemClicker {
        fun onDeleteClick(position: Int)
    }

    fun setItemClickListener(itemClicker: ItemClicker) {
        mListener = itemClicker
    }
}