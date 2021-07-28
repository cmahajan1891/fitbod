package com.example.fitbod.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitbod.data.model.ExerciseModel
import com.example.fitbod.repositories.ExercisesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExercisesViewModel(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {
    val exercises: MutableLiveData<List<ExerciseModel>> = MutableLiveData()
    fun onCreate() {
        if (exercises.value == null) {
            GlobalScope.launch(Dispatchers.Main) {
                exercisesRepository.getExercises {
                    exercises.postValue(getGroupedExercises(parseResponse(it)))
                }
            }
        }
    }

    private fun getGroupedExercises(list: List<ExerciseModel>): List<ExerciseModel> {
        return list.groupBy {
            it.name
        }.mapNotNull {
            it.value.maxByOrNull { entry -> entry.oneRepMax }
        }
    }

    private fun parseResponse(response: String): List<ExerciseModel> {
        Log.v("response", response)
        val entries = response.split("\n")

        return entries.mapNotNull {
            val entry = it.split(",")
            if (entry.size < 5) {
                return@mapNotNull null
            }
            val reps = entry[3].toInt()
            val weight = entry[4].toInt()
            val oneRepMax = weight / (1.0278 - 0.0278 * reps)
            ExerciseModel(
                date = entry[0],
                name = entry[1],
                set = entry[2].toInt(),
                reps = reps,
                weight = weight,
                oneRepMax = oneRepMax.toInt()
            )
        }
    }
}
