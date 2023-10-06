package com.example.todoapp

fun GroupsActivity.logoutUI()
{
    GroupsActivity.auth.signOut()
    Message("You are now offline")
    refreshLoginButton()
}