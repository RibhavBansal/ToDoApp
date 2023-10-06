package com.example.todoapp

import android.widget.Toast

fun GroupsActivity.Message(text: String)
{
    Toast.makeText(this,
                    text,
                    Toast.LENGTH_LONG)
        .show()
}