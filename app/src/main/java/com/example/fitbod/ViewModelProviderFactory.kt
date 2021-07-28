package com.example.fitbod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

fun <T : ViewModel> getViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    kClass: KClass<T>,
    creator: () -> T
) = ViewModelProvider(
    viewModelStoreOwner,
    object : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(kClass.java)) return creator() as T
            throw IllegalArgumentException("Unknown class name")
        }
    }
).get(kClass.java)
