package com.example.todoapp

import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.UserProfileChangeRequest

fun GroupsActivity.loginUI()
{
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Login")
    builder.setMessage("Please enter your email and password.")

    val layout = LinearLayout(this)
    layout.orientation = LinearLayout.VERTICAL

    val email = EditText(this)
    email.hint = "Email"
    layout.addView(email)

    val password = EditText(this)
    password.hint = "Password"
    layout.addView(password)

    builder.setView(layout)

    builder.setPositiveButton("Login"){_,_->
        loginOnCloud(email.text.toString(),
                        password.text.toString())
    }

    builder.setNegativeButton("Cancel"){_,_->}

    val dialog = builder.create()
    dialog.show()
}

fun GroupsActivity.loginOnCloud(email: String,
                                password: String)
{
    GroupsActivity.auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(this){
            if(it.isSuccessful)
            {
                val user = GroupsActivity.auth.currentUser
                Message("Welcome back ${user!!.displayName}")
                ReadFromCloud.read()
            }
            else
            {
                Log.i("TAG", it.exception.toString())
                Message("Login Failed")
            }
            refreshLoginButton()
        }
}