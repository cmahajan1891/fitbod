package com.example.fitbod.data.model

import java.util.*

data class ExerciseModel(
    val date: Date,
    val name: String,
    val set: Int,
    val reps: Int,
    val weight: Int,
    val oneRepMax: Int
)
