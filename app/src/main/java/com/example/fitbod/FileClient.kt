package com.example.fitbod

import android.util.Log
import java.io.BufferedReader

private const val TAG = "FileClient"

class FileClient constructor(private val application: FitbodApplication) : ReadFile {

    override fun readAssetFromFile(fileName: String): String = try {
        val stringBuilder = StringBuilder()
        this.application.assets.open(fileName).use {
            val bufferedReader = BufferedReader(it.reader())
            var line = bufferedReader.readLine()

            while (line != null) {
                stringBuilder.append(line)
                stringBuilder.append(System.getProperty("line.separator"))
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
        }

        stringBuilder.toString()
    } catch (e: Exception) {
        Log.e(TAG, "Unable to read asset - ${e.message}")
        ""
    }

}
