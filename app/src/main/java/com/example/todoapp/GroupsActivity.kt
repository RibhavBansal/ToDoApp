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
import java.io.File

class GroupsActivity : AppCompatActivity(), OnGroupClickListeners {
    private lateinit var groupsAdapter : GroupsAdapter
//    private var groupsAdapter : GroupsAdapter? = null

    private fun dbexists(): Boolean
    {
        return try {
            File(getDatabasePath(AppData.dbFileName).absolutePath).exists()
        }catch (e: Exception){
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

        val rv = findViewById<RecyclerView>(R.id.groupsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        groupsAdapter = GroupsAdapter(AppData.groups,this)
        rv.adapter = groupsAdapter

        if(dbexists()){

        }
        else{
            AppData.initialize()
            groupsAdapter = GroupsAdapter(AppData.groups,this)
            rv.adapter = groupsAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        groupsAdapter!!.notifyDataSetChanged()
    }

    fun createNewGroup(v : View) {
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

//        overridePendingTransition(R.anim.slide_in_right,
//                                    R.anim.slide_out_left)
    }

    override fun groupLongClicked(index: Int) {
        AppData.groups.removeAt(index)
        groupsAdapter!!.notifyItemRemoved(index)
    }
}