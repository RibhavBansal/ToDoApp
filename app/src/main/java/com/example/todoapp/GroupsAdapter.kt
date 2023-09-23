package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GroupsAdapter(private val list : List<Group>) : RecyclerView.Adapter<GroupsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return GroupsViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int)
    {
        val group = list[position]
        holder.bind(group)
    }

    override fun getItemCount(): Int = list.size

}