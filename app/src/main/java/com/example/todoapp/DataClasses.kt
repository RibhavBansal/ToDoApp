package com.example.todoapp

data class Item(
    var name: String,
    var completed: Boolean)
{
    constructor(map: HashMap<String,String>): this("",false)
    {
        this.name = map["name"] as String
        if(map["completed"].toString() == "true")
            this.completed = true
    }
}

data class Group(
    var name: String,
    var items: MutableList<Item>)
{
    constructor(map: HashMap<String,Any>): this("", mutableListOf())
    {
        this.name = map["name"] as String

        if(map["items"] != null) {
            var itemsInMap = map["items"] as HashMap<String, Any>
            for (each in itemsInMap.values) {
                each as HashMap<String, String>
                this.items.add(Item(each))
            }
        }
    }

    fun toMap(): HashMap<String,Any>
    {
        var groupMap = HashMap<String,Any>()
        groupMap["name"] = this.name

        var items = HashMap<String,Any>()
        for(item in this.items)
        {
            var eachItemMap = HashMap<String,String>()
            eachItemMap["name"] = item.name
            eachItemMap["completed"] = item.completed.toString()

            items[item.name] = eachItemMap
        }

        groupMap["items"] = items

        return groupMap
    }
}
