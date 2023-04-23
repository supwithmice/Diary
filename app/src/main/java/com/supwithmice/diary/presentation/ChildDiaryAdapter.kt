package com.supwithmice.diary.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supwithmice.diary.R
import com.supwithmice.diary.models.Lesson

class ChildDiaryAdapter (private val lessonList: List<Lesson>) :
    RecyclerView.Adapter<ChildDiaryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val lessonNameView: TextView
        private val lessonTimeView: TextView
        private val assingnmentView: TextView
        private val markTextView: TextView
        init {
            // Define click listener for the ViewHolder's View
            lessonNameView = view.findViewById(R.id.lessonNameView)
            lessonTimeView = view.findViewById(R.id.lessonTimeView)
            assingnmentView = view.findViewById(R.id.assingnmentView)
            markTextView = view.findViewById(R.id.markTextView)
        }
        fun bind(result: Lesson) {
            lessonNameView.text = result.subjectName
            lessonTimeView.text = "${result.startTime} - ${result.endTime}"
            assingnmentView.text = result.assignments?.get(0)?.assignmentName ?: ""
            markTextView.text =  result.assignments?.get(0)?.mark?.mark?.toString() ?: ""
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
        viewHolder.bind(lessonList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = lessonList.size

}
