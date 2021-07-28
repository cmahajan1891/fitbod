package com.example.fitbod.adapters

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbod.data.model.ExerciseModel
import com.example.fitbod.viewholders.ExerciseViewHolder

class ExerciseAdapter(
    parentLifecycle: Lifecycle,
    private val exercises: ArrayList<ExerciseModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExerciseViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExerciseViewHolder).bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun appendData(exercises: List<ExerciseModel>) {
        val oldCount = itemCount
        this.exercises.addAll(exercises)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount in 1 until currentCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }

}
