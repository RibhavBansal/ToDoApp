package com.example.todoapp

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReadFromCloud
{
    companion object
    {
        fun read()
        {
            if(GroupsActivity.auth.currentUser == null)
                return
            val uid = GroupsActivity.auth.uid!!

            AppData.groups.clear()

            GroupsActivity.database
                .getReference("Data")
                .child(uid)
                .child("groups")
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if(snapshot.value != null) {
                            val onlineData =
                                snapshot.value as HashMap<String, HashMap<String, Any>>

                            for (each in onlineData.values) {
                                val newGroup = Group(each)
                                AppData.groups.add(newGroup)
                            }
                            GroupsActivity.groupsAdapter!!.notifyDataSetChanged()
//                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("TAG", error.toException().toString())
                    }
                })
        }
    }
}