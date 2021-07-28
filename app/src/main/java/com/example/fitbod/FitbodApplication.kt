package com.example.fitbod

import android.app.Application
import com.example.fitbod.di.components.ApplicationComponent
import com.example.fitbod.di.components.DaggerApplicationComponent
import com.example.fitbod.di.modules.ApplicationModule

class FitbodApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        injectDependencies()
        super.onCreate()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this@FitbodApplication))
                .build()
        applicationComponent.inject(this@FitbodApplication)
    }
}