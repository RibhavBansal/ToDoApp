package com.example.todoapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import org.intellij.lang.annotations.Language

@Dao
interface TodoDao
{
    @Insert
    suspend fun insertGroup(group: Groups)

    @Insert
    suspend fun insertItem(item: Items)

    @Transaction @Query("SELECT * FROM Groups")
    suspend fun getGroupsWithItems(): MutableList<GroupWithItems>

    @Query("SELECT * FROM Items WHERE group_name = :groupName")
    suspend fun getItemsOfGroup(groupName: String) : MutableList<Items>

    @Query("DELETE FROM Groups WHERE group_name = :groupName")
    suspend fun deleteGroup(groupName: String)

    @Query("DELETE FROM Items WHERE group_name = :groupName AND item_name = :itemName")
    suspend fun deleteItem(groupName: String, itemName: String)

    @Query("DELETE FROM Items WHERE group_name = :groupName")
    suspend fun deleteItemsOfGroup(groupName: String)

    @Query("UPDATE Items SET completed = :completedVal WHERE item_name = :itemName AND group_name = :groupName")
    suspend fun updateItem(groupName: String, itemName: String, completedVal: Boolean)
}