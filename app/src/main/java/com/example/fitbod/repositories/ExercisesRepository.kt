package com.example.fitbod.repositories

import com.example.fitbod.FileClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExercisesRepository(
    private val fileName: String,
    private val readFileClient: FileClient
) {

    suspend fun getExercises(callback: (response: String) -> Unit) {

        withContext(Dispatchers.IO) {
            val response = readFileClient.readAssetFromFile(fileName)
            callback.invoke(response)
        }
    }

}
