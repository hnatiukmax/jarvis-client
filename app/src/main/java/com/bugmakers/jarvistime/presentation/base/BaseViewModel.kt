package com.bugmakers.jarvistime.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isProgressVisible = MutableLiveData<Boolean>()
}