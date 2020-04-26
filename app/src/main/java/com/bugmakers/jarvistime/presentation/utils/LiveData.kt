package com.bugmakers.jarvistime.presentation.utils

import androidx.lifecycle.MutableLiveData

inline fun <T> delegatedLiveData(crossinline initializer: () -> Any) = lazy {
    MutableLiveData<T>().also {
        initializer()
    }
}