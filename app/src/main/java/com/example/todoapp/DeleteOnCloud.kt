package com.example.todoapp

class DeleteOnCloud
{
    companion object
    {
        val uid = GroupsActivity.auth.uid!!

        fun deleteItem(item: Item, group: Group)
        {
            if(GroupsActivity.auth.currentUser == null)
                return

            GroupsActivity.database
                .getReference("Data")
                .child(uid)
                .child("groups")
                .child(group.name)
                .child("items")
                .child(item.name)
                .removeValue()
        }

        fun deleteGroup(group: Group)
        {
            if(GroupsActivity.auth.currentUser == null)
                return

            GroupsActivity.database
                .getReference("Data")
                .child(uid)
                .child("groups")
                .child(group.name)
                .removeValue()
        }
    }
}