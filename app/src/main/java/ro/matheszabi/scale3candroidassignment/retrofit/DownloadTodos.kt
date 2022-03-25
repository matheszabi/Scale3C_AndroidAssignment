package ro.matheszabi.scale3candroidassignment.retrofit

import android.util.Log
import ro.matheszabi.scale3candroidassignment.MyApplication
import ro.matheszabi.scale3candroidassignment.data.Todo

import kotlinx.coroutines.*


class DownloadTodos{


    /**
     * it will start the download process asynchronously
     */
    fun doDownloadWithPaginationInfoAsync(myApp: MyApplication) {
        val apiInterface =
            ApiInterface.create().getTodosWithPaginationInfo()// this it was specified!
        // async call:
        apiInterface.enqueue(DownloadTodosCallback(DownloadFinished(myApp)))
    }

    /**
     * When the callback succeeded, than write it  to the database.
     */
    private class DownloadFinished(val myApp: MyApplication) : DownloadingFinishedImpl(),  CoroutineScope by MainScope()
    {
        override fun onSuccess(paginationData: PaginationData, todoList: List<Todo>) {
            super.onSuccess(paginationData, todoList)
            if(todoList.isEmpty()){
                return
            }
            Log.d(javaClass.name, "Saving to database: ${todoList.size} fresh new todos downloaded")

            // write to database
            launch (Dispatchers.IO){
                // suspend function here:
                myApp.insertTodos(todoList)
                // notify the UI: it should reload from database
            }
        }
    }


}