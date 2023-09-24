package com.example.todoapp

interface OnGroupClickListeners
{
    fun groupClicked(index: Int)
    fun groupLongClicked(index: Int)
}

interface OnItemClickListeners
{
    fun itemClicked(index: Int)
    fun itemLongClicked(index: Int)
}