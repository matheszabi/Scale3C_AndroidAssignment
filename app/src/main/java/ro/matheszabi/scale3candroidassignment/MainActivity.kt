package ro.matheszabi.scale3candroidassignment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ro.matheszabi.scale3candroidassignment.retrofit.DownloadTodos
import ro.matheszabi.scale3candroidassignment.todolistui.ItemsViewModel
import ro.matheszabi.scale3candroidassignment.todolistui.RecyclerItemClickListener
import ro.matheszabi.scale3candroidassignment.todolistui.RecyclerViewAdapter
import ro.matheszabi.scale3candroidassignment.util.ConnectionChecker.Companion.isOnline


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {


    lateinit var recyclerView: RecyclerView
    lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)

        // add an empty data set:
        val data = ArrayList<ItemsViewModel>()
        val adapter = RecyclerViewAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener())

        swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
        swipeLayout.setOnRefreshListener(myRefreshListener)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(myDatabaseChangedReceiver, IntentFilter(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED))


        loadFromDatabase()

        // check the connection and if it is, than download todos
        if (isOnline(this)) {
            this.launch(Dispatchers.IO) {

                val myApp = application as MyApplication
                DownloadTodos().doDownloadWithPaginationInfoAsync(myApp)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(myDatabaseChangedReceiver)
    }

    private fun loadFromDatabase(){
        Log.d("MainActivity", "loading data from database")
        val myApp = application as MyApplication

        launch {
            swipeLayout.isRefreshing = true
            val todosLiveData = myApp.getAllTodos()
            todosLiveData.observe(this@MainActivity) {
                val data = ArrayList<ItemsViewModel>(it.size)
                for (todo in it) {
                    // drop  all unnecessary information and allow a lot more larger list in memory
                    // for the price of reading from database when is needed the detail ( by id)
                    data.add(ItemsViewModel(todo.title, todo.id.toString()))
                }
                val adapter = RecyclerViewAdapter(data)
                recyclerView.adapter = adapter
                swipeLayout.isRefreshing = false
            }
        }
    }



    private var myRefreshListener = OnRefreshListener {

        Log.d("MainActivity", "OnRefreshListener")
        swipeLayout.isRefreshing = false
        // check the connection and if is online, than download
        if (isOnline(this)) {
            this.launch(Dispatchers.IO) {
                val myApp = application as MyApplication

                swipeLayout.isRefreshing = true
                DownloadTodos().doDownloadWithPaginationInfoAsync(myApp)
            }
        }
    }

    private var myDatabaseChangedReceiver = object : DatabaseChangedReceiver(){

        override fun onReceive(context: Context, intent: Intent) {
            // update your list
            Log.d("MainActivity", "received event to load data from database")

            this@MainActivity.loadFromDatabase()
        }
    }



}