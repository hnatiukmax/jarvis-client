package com.bugmakers.jarvistime.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

internal fun MutableLiveData<Boolean>.enable() {
    this.value = true
}

internal fun MutableLiveData<Boolean>.disable() {
    this.value = false
}

internal val LiveData<Boolean>.isRight : Boolean
    get() = this.value ?: false

internal val LiveData<String>.valueOrEmpty : String
    get() = this.value ?: ""

