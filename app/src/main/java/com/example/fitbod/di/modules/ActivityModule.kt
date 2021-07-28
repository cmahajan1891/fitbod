package com.example.fitbod.di.modules

import android.app.Activity
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fitbod.di.scopes.ActivityScope
import com.example.fitbod.getViewModel
import com.example.fitbod.repositories.ExercisesRepository
import com.example.fitbod.viewmodels.ExercisesViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: Activity,
    private val viewModelStoreOwner: ViewModelStoreOwner
) {

    @ActivityScope
    @Provides
    fun providesViewModel(exercisesRepository: ExercisesRepository) = getViewModel(
        viewModelStoreOwner,
        ExercisesViewModel::class
    ) {
        ExercisesViewModel(exercisesRepository)
    }

}
