package com.supwithmice.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryAdapter (private val dataSet: Array<String>) :
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lessonNameView: TextView
        val lessonTimeView: TextView
        val assingnmentView: TextView
        val markImageView: ImageView


        init {
            // Define click listener for the ViewHolder's View
            lessonNameView = view.findViewById(R.id.lessonNameView)
            lessonTimeView = view.findViewById(R.id.lessonTimeView)
            assingnmentView = view.findViewById(R.id.assingnmentView)
            markImageView = view.findViewById(R.id.markImageView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.diary_recycler_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.lessonNameView.text = dataSet[position]
        viewHolder.lessonTimeView.text = dataSet[position]
        viewHolder.assingnmentView.text = dataSet[position]
//        viewHolder.markImageView.drawable =
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
