package com.example.todoapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity(), OnItemClickListeners {
    private lateinit var currGroup : Group
    private lateinit var itemsAdapter : ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items)

        val selectedIndex = intent.getIntExtra("groupIndex",0)
        currGroup = AppData.groups[selectedIndex]

        var title = findViewById<TextView>(R.id.toolbarTitle)
        var bar : Toolbar? = findViewById<Toolbar>(R.id.myToolbar)

        title.text = currGroup.name

        var rv = findViewById<RecyclerView>(R.id.itemsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        itemsAdapter = ItemsAdapter(currGroup, this)
        rv.adapter = itemsAdapter

        setSupportActionBar(bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var editText = findViewById<EditText>(R.id.newItemEditText)
        editText.setOnKeyListener { view, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER)
            {
                if(event.action == KeyEvent.ACTION_DOWN)
                {
                    val name: String = editText.text.toString()
                    val item = Item(name,false)
                    currGroup.items.add(item)
                    itemsAdapter.notifyItemInserted(currGroup.items.count())
                    editText.text.clear()

                    val inputManager =
                        getSystemService(Activity.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    inputManager.hideSoftInputFromWindow(view.windowToken,0)
                }
            }
            false
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun finish() {
        super.finish()
//        overridePendingTransition(R.anim.slide_in_left,
//                                    R.anim.slide_out_right)
    }

    override fun itemClicked(index: Int) {
        currGroup.items[index].completed = !currGroup.items[index].completed
        itemsAdapter!!.notifyDataSetChanged()
    }

    override fun itemLongClicked(index: Int) {
        currGroup.items.removeAt(index)
        itemsAdapter.notifyItemRemoved(index)
    }
}