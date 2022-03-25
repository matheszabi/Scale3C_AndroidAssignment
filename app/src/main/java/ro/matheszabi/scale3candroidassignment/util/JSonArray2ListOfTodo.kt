package ro.matheszabi.scale3candroidassignment.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ro.matheszabi.scale3candroidassignment.data.Todo

class JSonArray2ListOfTodo {

    companion object {
        /**
         * should be used to convert REST response body JsonArray to List of Todo
         */
        fun buildList(jsonArray: JsonArray): List<Todo> {
            val list = ArrayList<Todo>(jsonArray.size())
            val iterator = jsonArray.iterator()
            while (iterator.hasNext()) {
                val jsonObj = iterator.next() as JsonObject
                val todo = Todo(
                    jsonObj["id"].asLong,
                    jsonObj["user_id"].asInt,
                    jsonObj["title"].asString,
                    jsonObj["due_on"].asString,
                    jsonObj["status"].asString
                )
                list.add(todo)
            }
            return list
        }
    }
}