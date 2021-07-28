package com.example.fitbod

import javax.inject.Singleton

@Singleton
interface ReadFile {
    fun readAssetFromFile(fileName: String): String
}
