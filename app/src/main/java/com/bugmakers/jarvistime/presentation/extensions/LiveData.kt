package com.bugmakers.jarvistime.presentation.extensions

import androidx.lifecycle.MutableLiveData

internal fun MutableLiveData<Boolean>.enable() {
    this.value = true
}

internal fun MutableLiveData<Boolean>.diable() {
    this.value = false
}