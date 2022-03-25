package ro.matheszabi.scale3candroidassignment

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import ro.matheszabi.scale3candroidassignment.data.AppDatabase
import ro.matheszabi.scale3candroidassignment.data.Todo

class MyApplication : Application() {

    private lateinit var androidId: String
    private lateinit var database: AppDatabase

    @SuppressLint("HardwareIds")// move to advertising id, if you want
    override fun onCreate() {
        super.onCreate()

        // kinda device id: factory reset can be null
        androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d(
            packageName,
            "Device ID: $androidId"
        )

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo_database"
        ).allowMainThreadQueries()
            .build()
    }

    //region MakeItWithSuspend this local database access functions, if you want

    fun insertTodos(todoList: List<Todo>) {

        val todoDao = database.todoDao()
        todoDao.insertAll(todoList)

        // notify about the changes:
        Log.d("MyApplication", "Fire event ACTION_DATABASE_CHANGED");
        // send broadcast only inside of application:
        LocalBroadcastManager.getInstance(this).
        sendBroadcast( Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));
    }

    fun getAllTodos(): LiveData<List<Todo>> {
        return database.todoDao().loadAllTodos()
    }
    //endregion



}