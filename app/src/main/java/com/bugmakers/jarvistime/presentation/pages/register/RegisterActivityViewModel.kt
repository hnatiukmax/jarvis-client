package com.bugmakers.jarvistime.presentation.pages.register

import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.presentation.base.BaseViewModel

internal class RegisterActivityViewModel : BaseViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    fun onRegisterClick() {

    }
}