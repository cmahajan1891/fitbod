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
        GlobalScope.launch(Dispatchers.Main) {
            exercisesRepository.getExercises {
                exercises.postValue(parseResponse(it))
            }
        }
    }

    private fun parseResponse(response: String): List<ExerciseModel> {
        Log.v("response", response)
        return emptyList()
    }
}
