package ro.matheszabi.scale3candroidassignment.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.matheszabi.scale3candroidassignment.data.Todo
import ro.matheszabi.scale3candroidassignment.util.JSonArray2ListOfTodo

/**
 * The retrofit regular callback, it will store the pagination data and the todolist on successfully download
 */
class DownloadTodosCallback (val downloadingFinished: DownloadingFinishedInterface?): Callback<JsonObject> {

    var todoList : List<Todo>? = null
    var paginationData : PaginationData? = null

    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
        if (response?.body() != null) {
            when {
                response.isSuccessful -> {
                    // meta:
                    val meta = response.body()!!["meta"] as JsonObject
                    // pagination: - use it to get the next page, if you want!
                    val pagination = meta["pagination"] as JsonObject
                    // in the comment there are different values, what  I got already
                    val total = pagination["total"].asInt//1797 , 16871
                    val pages = pagination["pages"].asInt//90 , 85
                    val page = pagination["page"].asInt//1
                    val limit = pagination["limit"].asInt//20
                    paginationData = PaginationData(total, pages, page, limit)

                    // data:
                    val data = response.body()!!["data"] as JsonArray
                    // parsed to list of objects:
                    todoList = JSonArray2ListOfTodo.buildList(data) // List<Todo>
                    // call OnSuccess
                    downloadingFinished?.onSuccess(paginationData!!, todoList!!)
                }
                else -> {
                    downloadingFinished?.onError()// response not success
                }
            }
        }
    }

    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
        downloadingFinished?.onError() // error at connection
    }
}