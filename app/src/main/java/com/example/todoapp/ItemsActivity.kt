package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items)


        val selectedIndex = intent.getIntExtra("groupIndex",0)
        var currGroup = AppData.groups[selectedIndex]

        var title = findViewById<TextView>(R.id.toolbarTitle)
        var bar : Toolbar? = findViewById<Toolbar>(R.id.myToolbar)

        title.text = currGroup.name

        setSupportActionBar(bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}