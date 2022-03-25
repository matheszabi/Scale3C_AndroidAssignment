package ro.matheszabi.scale3candroidassignment.retrofit

import android.util.Log
import ro.matheszabi.scale3candroidassignment.data.Todo

open class DownloadingFinishedImpl : DownloadingFinishedInterface {

    override fun onError() {
        // log it to debug:
        Log.e(javaClass.name, "onError() -because download is failed")
    }

    override fun onSuccess(
        paginationData: PaginationData,
        todoList: List<Todo>
    ) {
        // log it to debug:
        Log.d(javaClass.name, "onSuccess() \n$paginationData \n$todoList")
    }
}