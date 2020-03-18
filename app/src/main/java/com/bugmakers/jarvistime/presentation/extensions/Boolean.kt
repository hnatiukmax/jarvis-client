package com.bugmakers.jarvistime.presentation.extensions

internal infix fun Boolean?.AND(comparable : Boolean?) =
    this == true && comparable == true

internal infix fun Boolean?.OR(comparable : Boolean?) =
    this == true || comparable == true