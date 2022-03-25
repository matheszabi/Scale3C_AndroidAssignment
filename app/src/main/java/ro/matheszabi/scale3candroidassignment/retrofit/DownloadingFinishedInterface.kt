package ro.matheszabi.scale3candroidassignment.retrofit

import ro.matheszabi.scale3candroidassignment.data.Todo

interface DownloadingFinishedInterface {
    fun onError()
    fun onSuccess(paginationData: PaginationData, todoList: List<Todo>)
}