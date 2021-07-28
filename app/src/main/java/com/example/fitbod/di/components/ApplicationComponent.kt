package com.example.fitbod.di.components

import com.example.fitbod.FileClient
import com.example.fitbod.FitbodApplication
import com.example.fitbod.di.modules.ApplicationModule
import com.example.fitbod.repositories.ExercisesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: FitbodApplication)
    fun getReadFileClient(): FileClient
    fun getExercisesRepository(): ExercisesRepository
}

