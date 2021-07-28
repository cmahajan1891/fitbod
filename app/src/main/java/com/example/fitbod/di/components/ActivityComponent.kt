package com.example.fitbod.di.components

import com.example.fitbod.activity.MainActivity
import com.example.fitbod.di.modules.ActivityModule
import com.example.fitbod.di.scopes.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

}
