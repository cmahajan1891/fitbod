package com.example.fitbod.di.components

import com.example.fitbod.di.modules.FragmentModule
import com.example.fitbod.di.scopes.FragmentScope
import com.example.fitbod.fragments.Exercises
import dagger.Component

@FragmentScope
@Component(modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: Exercises)

}
