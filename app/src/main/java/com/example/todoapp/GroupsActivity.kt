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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.groups.*
import kotlinx.coroutines.*
import java.io.File

class GroupsActivity : AppCompatActivity(), OnGroupClickListeners {
    //    private var groupsAdapter : GroupsAdapter? = null

    companion object
    {
        var groupsAdapter : GroupsAdapter? = null
        lateinit var auth: FirebaseAuth
        val database = Firebase.database
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

        auth = FirebaseAuth.getInstance()
        AppData.initialize()

        val rv = findViewById<RecyclerView>(R.id.groupsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        groupsAdapter = GroupsAdapter(AppData.groups,this)
        rv.adapter = groupsAdapter
    }

    override fun onResume()
    {
        super.onResume()
        groupsAdapter!!.notifyDataSetChanged()

        refreshLoginButton()

        if(auth.currentUser != null)
        {
            ReadFromCloud.read()
        }
    }

    fun createNewGroup(v : View)
    {
        if(GroupsActivity.auth.currentUser == null) {
            Message("Please login first!!")
            return
        }

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
            SaveOnCloud.saveGroup(newGroup)
        }

        builder.setNegativeButton("Cancel")
        {_,_ ->

        }

        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    override fun groupClicked(index: Int)
    {
        if(GroupsActivity.auth.currentUser == null) {
            Message("Please login first!!")
            return
        }

        val intent = Intent(this,ItemsActivity::class.java)

        intent.putExtra("groupIndex",index)

        startActivity(intent)

//        overridePendingTransition(R.anim.slide_in_right,
//                                    R.anim.slide_out_left)
    }

    override fun groupLongClicked(index: Int)
    {
        val groupName = AppData.groups[index].name

        DeleteOnCloud.deleteGroup(AppData.groups[index])

        AppData.groups.removeAt(index)
        groupsAdapter!!.notifyItemRemoved(index)
        groupsAdapter!!.notifyItemRangeChanged(index,AppData.groups.count())
    }

    fun cloudFunctions(v: View)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login Actions")
        builder.setMessage("What you like to do?")

        if(auth.currentUser == null){
            builder.setPositiveButton("Login"){
                    _,_-> loginUI()
            }

            builder.setNeutralButton("Register"){
                    _,_-> registerUI()
            }
        }
        else{
            builder.setPositiveButton("Logout"){
                    _,_-> logoutUI()
            }
        }

        builder.setNegativeButton("Cancel"){
            _,_->
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun refreshLoginButton()
    {
        if(auth.currentUser == null)
        {
            profileButton.text = "Login!"
            profileButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                                                                    R.color.orange))
        }
        else
        {
            profileButton.text = "Online!"
            profileButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                                                                    R.color.green))
        }
    }
}

