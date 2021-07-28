package com.example.fitbod.data.model

import java.util.*

// TODO check if date needs to be stored as a long/string
data class ExerciseModel(
    val date: Date,
    val name: String,
    val set: Int,
    val reps: Int,
    val weight: Int,
    val oneRepMax: Int
)
