package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupsViewHolder (inflater : LayoutInflater,
                        parent : ViewGroup
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.group_row,
        parent,
        false))
{
    private var groupNameTextView : TextView? = null
    private var groupCountTextView : TextView? = null

    init {
        groupNameTextView = itemView.findViewById(R.id.groupNameTextView)
        groupCountTextView = itemView.findViewById(R.id.groupCountTextView)
    }

    fun bind(groupWithItems: GroupWithItems)
    {
        groupNameTextView!!.text = groupWithItems.group.name
        groupCountTextView!!.text = "${groupWithItems.items.count()} items"
    }

}