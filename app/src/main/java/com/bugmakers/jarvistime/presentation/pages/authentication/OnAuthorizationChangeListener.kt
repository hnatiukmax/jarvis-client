package com.bugmakers.jarvistime.presentation.pages.authentication

import com.bugmakers.jarvistime.presentation.entity.enums.AuthorizationFragmentType

internal interface OnAuthorizationChangeListener {

    fun onAuthorizationFragmentChanged(changeTo: AuthorizationFragmentType)
}