package com.bugmakers.jarvistime.presentation.entity

import com.bugmakers.jarvistime.presentation.utils.StringResource
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage

internal data class AppUIMessage(
    val typeMessage: TypeUIMessage,
    val stringResource: StringResource
)