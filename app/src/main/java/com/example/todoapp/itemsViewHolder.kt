package com.example.todoapp

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsViewHolder(inflater: LayoutInflater,
                      parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_row,
                                                parent,
                                                false))
{
    private var itemNameTextView : TextView? = null
    private var itemCheckBox : CheckBox? = null

    init {
        itemNameTextView = itemView.findViewById(R.id.itemNameTextView)
        itemCheckBox = itemView.findViewById(R.id.itemCheckBox)

//        itemCheckBox?.setOnClickListener {
//            if(itemCheckBox!!.isChecked) {
//                itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags or
//                        Paint.STRIKE_THRU_TEXT_FLAG
//                itemView.setBackgroundColor(Color.LTGRAY)
//            }
//            else{
//                itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags and
//                        Paint.STRIKE_THRU_TEXT_FLAG.inv()
//                itemView.setBackgroundColor(Color.TRANSPARENT)
//            }
//        }
    }

    fun bind(item : Item, group: Group)
    {
        itemCheckBox!!.setOnClickListener{
            item.completed = itemCheckBox!!.isChecked
            if(itemCheckBox!!.isChecked) {
                itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags or
                        Paint.STRIKE_THRU_TEXT_FLAG
                itemView.setBackgroundColor(Color.LTGRAY)
            }
            else{
                itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags and
                        Paint.STRIKE_THRU_TEXT_FLAG.inv()
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }
            SaveOnCloud.saveItem(item,group)
        }

        itemNameTextView!!.text = item.name
        itemCheckBox!!.isChecked = item.completed

        if(item.completed)
        {
            itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags or
                    Paint.STRIKE_THRU_TEXT_FLAG
            itemView.setBackgroundColor(Color.LTGRAY)
        }
        else
        {
            itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags and
                    Paint.STRIKE_THRU_TEXT_FLAG.inv()
            itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}