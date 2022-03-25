package ro.matheszabi.scale3candroidassignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.ZonedDateTime


@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val user_id:Int,
    val title: String,
    val due_on: String,
    val status: String) {
}