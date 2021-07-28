package com.example.fitbod.di.modules

import com.example.fitbod.BuildConfig
import com.example.fitbod.FileClient
import com.example.fitbod.FitbodApplication
import com.example.fitbod.repositories.ExercisesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: FitbodApplication) {

//    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        fun provideSharedPreferences(
//            app: Application
//        ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
//    }

//    @Singleton
//    @Provides
//    fun providesApplicationContext(): Context = app

    @Provides
    @Singleton
    fun providesExercisesRepository(fileClient: FileClient) = ExercisesRepository(
        BuildConfig.FILE_NAME,
        fileClient
    )

    @Provides
    @Singleton
    fun providesReadFileClient() = FileClient(application)
}
