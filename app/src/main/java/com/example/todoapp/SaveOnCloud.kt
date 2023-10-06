package com.example.todoapp

import android.content.ClipData.Item
import android.util.Log
import com.google.firebase.database.DatabaseReference

class SaveOnCloud
{
    companion object
    {
        val uid = GroupsActivity.auth.uid!!

        fun saveItem(item: com.example.todoapp.Item, group: com.example.todoapp.Group)
        {
            if(GroupsActivity.auth.currentUser == null)
                return

            GroupsActivity.database.getReference("Data")
                .child(uid)
                .child("groups")
                .child(group.name)
                .child("items")
                .child(item.name)
                .setValue(item, DatabaseReference.CompletionListener{
                    error,ref->
                    if(error == null)
                    {
                        Log.i("TAG", "Write Succeeded")
                    }
                    else
                    {
                        Log.i("TAG", "Write Failed")
                    }
                })
        }

        fun saveGroup(group: com.example.todoapp.Group)
        {
            if(GroupsActivity.auth.currentUser == null)
                return

            GroupsActivity.database.getReference("Data")
                .child(uid)
                .child("groups")
                .child(group.name)
                .setValue(group, DatabaseReference.CompletionListener{
                    error,ref->
                    if(error == null)
                    {
                        Log.i("TAG", "Write Succeeded")
                    }
                    else
                    {
                        Log.i("TAG", "Write Failed")
                    }
                })
        }

        fun saveGroupMap(map: HashMap<String,Any>)
        {
            if(GroupsActivity.auth.currentUser == null)
                return

            GroupsActivity.database.getReference("Data")
                .child(uid)
                .child("groups")
                .child(map["name"] as String)
                .setValue(map, DatabaseReference.CompletionListener{
                        error,ref->
                    if(error == null)
                    {
                        Log.i("TAG", "Write Succeeded")
                    }
                    else
                    {
                        Log.i("TAG", "Write Failed")
                    }
                })
        }
    }
}