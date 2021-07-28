package com.example.fitbod.data.model

// TODO check if date needs to be stored as a long/string
data class ExerciseModel(
    val name: String,
    val date: String,
    val set: Int,
    val reps: Int,
    val weight: Int
)
