package ro.matheszabi.scale3candroidassignment.todolistui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.matheszabi.scale3candroidassignment.R

// the "modern" ListView ...
class RecyclerViewAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvId: TextView = itemView.findViewById(R.id.tv_id_ghost)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.tvTitle.text = itemsViewModel.title

        // sets the text to the textview from our itemHolder class
        holder.tvId.text = itemsViewModel.id
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}