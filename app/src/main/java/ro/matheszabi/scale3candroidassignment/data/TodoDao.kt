package ro.matheszabi.scale3candroidassignment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos ORDER BY due_on")
    fun loadAllTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE id = :todoId")
    fun getTodo(todoId: Long): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todoList: List<Todo>)
}