package ro.matheszabi.scale3candroidassignment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

 open class DatabaseChangedReceiver : BroadcastReceiver() {


     override fun onReceive(context: Context, intent: Intent) {}

    companion object {
        var ACTION_DATABASE_CHANGED = "ro.matheszabi.scale3candroidassignment.DATABASE_CHANGED"
    }
}