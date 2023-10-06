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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsActivity : AppCompatActivity(), OnItemClickListeners {
    private lateinit var thisGroup: Group
    private lateinit var itemsAdapter : ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items)

        val selectedIndex = intent.getIntExtra("groupIndex",0)
        thisGroup = AppData.groups[selectedIndex]

//        ToolBar
        var bar : Toolbar? = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(bar)

//        Title TextView
        var title = findViewById<TextView>(R.id.toolbarTitle)
        title.text = thisGroup.name

        var rv = findViewById<RecyclerView>(R.id.itemsRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        itemsAdapter = ItemsAdapter(thisGroup, this)
        rv.adapter = itemsAdapter

        var editText = findViewById<EditText>(R.id.newItemEditText)
        editText.setOnKeyListener { view, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER)
            {
                if(event.action == KeyEvent.ACTION_DOWN)
                {
                    val name: String = editText.text.toString()
                    val item = Item(name,false)
                    thisGroup.items.add(item)

                    itemsAdapter.notifyItemInserted(thisGroup.items.count())
                    editText.text.clear()

                    SaveOnCloud.saveItem(item, thisGroup)

                    val inputManager =
                        getSystemService(Activity.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    inputManager.hideSoftInputFromWindow(view.windowToken,0)
                }
            }
            false
        }

//        For Back Button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
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
        val item = thisGroup.items[index]
        item.completed = !(item.completed)

        itemsAdapter!!.notifyDataSetChanged()

        SaveOnCloud.saveItem(item,thisGroup)
    }

    override fun itemLongClicked(index: Int) {
        val groupName = thisGroup.name
        val itemName = thisGroup.items[index].name

        DeleteOnCloud.deleteItem(thisGroup.items[index],thisGroup)

        thisGroup.items.removeAt(index)
        itemsAdapter.notifyItemRemoved(index)
    }
}