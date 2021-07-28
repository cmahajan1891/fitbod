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
    private val exerciseOneRepMax: TextView

    init {
        exerciseName = itemView.findViewById(R.id.exercise_name) as MaterialTextView
        exerciseOneRepMax = itemView.findViewById(R.id.exercise_one_rep_max) as MaterialTextView
        itemView.setOnClickListener {
            itemView.findNavController().navigate(
                ExercisesDirections.actionExercisesToDetailFragment(
                    exerciseName = exerciseName.text.toString(),
                    oneRepMax = exerciseOneRepMax.text.toString()
                )
            )
        }
    }

    fun bind(data: ExerciseModel) {
        exerciseName.text = data.name
        exerciseOneRepMax.text = data.oneRepMax.toString()
    }

}
