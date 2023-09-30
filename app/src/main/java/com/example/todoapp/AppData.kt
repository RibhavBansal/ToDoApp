package com.example.todoapp

import androidx.room.Database

class AppData
{
    companion object DataHolder
    {
        var dbFileName = "Todo_db"
        lateinit var db : TodoDatabase

        var groups: MutableList<GroupWithItems> = mutableListOf()

        fun initialize()
        {
            val group1 = Groups("Home")
            val item1 = Items("Bread", group1.name,false)
            val item2 = Items("Milk", group1.name, true)

            val group2 = Groups("Training")
            val item3 = Items("Tap to Cross", group2.name, false)
            val item4 = Items("Long press to Delete", group2.name, true)

            val groupWithItems1 = GroupWithItems(group1, mutableListOf(item1, item2))
            val groupWithItems2 = GroupWithItems(group2, mutableListOf(item3, item4))

            groups = mutableListOf(groupWithItems1, groupWithItems2)
        }
    }
}