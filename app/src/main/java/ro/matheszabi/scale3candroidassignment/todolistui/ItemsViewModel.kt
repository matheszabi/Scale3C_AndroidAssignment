package ro.matheszabi.scale3candroidassignment.todolistui

import androidx.lifecycle.ViewModel

// this is "the model" at "the view"
data class ItemsViewModel(val title: String, val id: String): ViewModel() {
    // add or remove to ui functions here
}