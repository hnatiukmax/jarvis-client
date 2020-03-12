package com.bugmakers.jarvistime.presentation.pages.login

import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.presentation.base.BaseViewModel

internal class LogInActivityViewModel : BaseViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLogInClick() {

    }
}