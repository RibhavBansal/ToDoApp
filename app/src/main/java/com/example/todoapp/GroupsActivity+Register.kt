package com.example.todoapp

import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.UserProfileChangeRequest

fun GroupsActivity.registerUI()
{
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Register")
    builder.setMessage("Please enter your name, email and password.")

    val layout = LinearLayout(this)
    layout.orientation = LinearLayout.VERTICAL

    val name = EditText(this)
    name.hint = "Name"
    layout.addView(name)

    val email = EditText(this)
    email.hint = "Email"
    layout.addView(email)

    val password = EditText(this)
    password.hint = "Password"
    layout.addView(password)

    builder.setView(layout)

    builder.setPositiveButton("Register"){_,_->
        registerOnCloud(name.text.toString(),
                        email.text.toString(),
                        password.text.toString())
    }

    builder.setNegativeButton("Cancel"){_,_->}

    val dialog = builder.create()
    dialog.show()
}

fun GroupsActivity.registerOnCloud(name: String,
                                  email: String,
                                  password: String)
{
    GroupsActivity.auth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(this){
            if(it.isSuccessful)
            {
                val user = GroupsActivity.auth.currentUser
                val updates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build()
                user!!.updateProfile(updates)

                Message("Registration Succeeded")

                for (group in AppData.groups)
                {
                    val newMap = group.toMap()
                    SaveOnCloud.saveGroupMap(newMap)
                }
            }
            else
            {
                Log.i("TAG", it.exception.toString())
                Message("Registration Failed")
            }
            refreshLoginButton()
        }
}