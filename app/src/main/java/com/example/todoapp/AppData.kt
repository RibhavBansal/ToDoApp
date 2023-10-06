package com.example.todoapp
class AppData
{
    companion object DataHolder
    {
        var groups: MutableList<Group> = mutableListOf()
        fun initialize()
        {
            val item1 = Item("To",false)
            val item2 = Item("Do",true)

//            val item3 = Item("Tap to Cross",false)
//            val item4 = Item("Long press to Delete",true)

            val group1 = Group("ToDo", mutableListOf(item1, item2))
//            val group2 = Group("Training", mutableListOf(item3, item4))

            groups = mutableListOf(group1)
        }
    }
}