package com.example.fitbod.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fitbod.di.scopes.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(
    private val fragment: Fragment,
    private val viewModelStoreOwner: ViewModelStoreOwner
) {

    @FragmentScope
    @Provides
    fun providesContext(): Context = fragment.requireContext()

//    @FragmentScope
//    @Provides
//    fun providesFirstFragmentViewModel() = getViewModel(
//        viewModelStoreOwner,
//        ExercisesViewModel::class
//    ) {
//        ExercisesViewModel()
//    }

//    @Provides
//    @FragmentScope
//    fun provideBackgroundAdapter() = BackgroundAdapter(fragment.lifecycle, ArrayList())

//    @FragmentScope
//    @Provides
//    fun providesSecondFragmentViewModel() = getViewModel(
//        viewModelStoreOwner,
//        SecondFragmentViewModel::class
//    ) {
//        SecondFragmentViewModel()
//    }

}
