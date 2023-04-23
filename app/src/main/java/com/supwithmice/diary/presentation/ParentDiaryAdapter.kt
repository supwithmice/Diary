package com.supwithmice.diary.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supwithmice.diary.R
import com.supwithmice.diary.models.WeekDay


class ParentDiaryAdapter(): RecyclerView.Adapter<ParentDiaryAdapter.ViewHolder>() {
    var dayList: List<WeekDay> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayView: TextView
        private val childDiaryView: RecyclerView

        init {
            dayView = itemView.findViewById(R.id.dayTextView)
            childDiaryView = itemView.findViewById(R.id.childDiaryRecyclerView)
        }

        fun bind(result: WeekDay) {
            dayView.text = result.date.substring(0, 10)
            val childDiaryAdapter = ChildDiaryAdapter(result.lessons)
            childDiaryView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,false)
            childDiaryView.adapter = childDiaryAdapter
            val mDividerItemDecoration = DividerItemDecoration(
                childDiaryView.context,
                LinearLayoutManager.VERTICAL
            )
            childDiaryView.addItemDecoration(mDividerItemDecoration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.diary_parent_recycler_view_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    fun addData(list: List<WeekDay>) {
        dayList = list
        notifyDataSetChanged()
    }
}