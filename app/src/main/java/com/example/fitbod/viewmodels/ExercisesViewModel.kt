package com.example.fitbod.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitbod.data.model.ExerciseModel
import com.example.fitbod.repositories.ExercisesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "MMM dd yyyy"

class ExercisesViewModel(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    companion object {
        @SuppressLint("ConstantLocale")
        private val DATE_FORMATTER = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    }

    val exerciseMap = MutableLiveData<Map<String, List<ExerciseModel>>>()

    fun onCreate() {
        if (exerciseMap.value == null) {
            GlobalScope.launch(Dispatchers.Main) {
                exercisesRepository.getExercises {
                    val groupedExercises = getGroupedExercises(parseResponse(it))
                    exerciseMap.postValue(groupedExercises)
                }
            }
        }
    }

    private fun getGroupedExercises(list: List<ExerciseModel>): Map<String, List<ExerciseModel>> {
        return list.groupBy {
            it.name
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
            val oneRepMax = weight / ((1.0278 - 0.0278) * reps)
            val date = stringToDate(entry[0])
            date?.let {
                ExerciseModel(
                    date = date,
                    name = entry[1],
                    set = entry[2].toInt(),
                    reps = reps,
                    weight = weight,
                    oneRepMax = oneRepMax.toInt()
                )
            }
        }
    }

    private fun stringToDate(dateString: String, format: SimpleDateFormat = DATE_FORMATTER): Date? {
        dateString.let {
            try {
                return format.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return null
    }

}
