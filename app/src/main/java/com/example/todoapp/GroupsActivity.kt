package com.example.todoapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

        val rv = findViewById<RecyclerView>(R.id.groupsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        AppData.initialize()
        rv.adapter = GroupsAdapter(AppData.groups)
    }
}