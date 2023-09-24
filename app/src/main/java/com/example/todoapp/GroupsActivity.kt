package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsActivity : AppCompatActivity(), OnGroupClickListeners {
    private var groupsAdapter : GroupsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

        val rv = findViewById<RecyclerView>(R.id.groupsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        AppData.initialize()
        groupsAdapter = GroupsAdapter(AppData.groups,this)
        rv.adapter = groupsAdapter
    }

    fun createNewGroup(v : View)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Group")
        builder.setMessage("Enter the name of group")

        val inputText = EditText(this)
        inputText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(inputText)

        builder.setPositiveButton("Save")
        {_,_ ->
            val groupName = inputText.text.toString()
            val newGroup = Group(groupName, mutableListOf())

            AppData.groups.add(newGroup)
            groupsAdapter!!.notifyItemInserted(AppData.groups.count())
        }

        builder.setNegativeButton("Cancel")
        {_,_ ->

        }

        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    override fun groupClicked(index: Int) {
        val intent = Intent(this,ItemsActivity::class.java)

        intent.putExtra("groupIndex",index)

        startActivity(intent)
    }

    override fun groupLongClicked(index: Int) {

    }
}