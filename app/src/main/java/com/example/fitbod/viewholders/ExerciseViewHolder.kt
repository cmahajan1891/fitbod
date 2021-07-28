package com.example.fitbod.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbod.R
import com.example.fitbod.data.model.ExerciseModel
import com.example.fitbod.fragments.ExercisesDirections
import com.google.android.material.textview.MaterialTextView

class ExerciseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)
) {

    private val exerciseName: TextView
    private val exerciseWeight: TextView

    init {
        exerciseName = itemView.findViewById(R.id.exercise_name) as MaterialTextView
        exerciseWeight = itemView.findViewById(R.id.exercise_weight) as MaterialTextView
        itemView.setOnClickListener {
            itemView.findNavController().navigate(
                ExercisesDirections.actionExercisesToDetailFragment(
                    exerciseName.text.toString(),
                    exerciseWeight.text.toString()
                )
            )
        }
    }

    fun bind(data: ExerciseModel) {
        exerciseName.text = data.name
        exerciseWeight.text = data.weight.toString()
    }

}
