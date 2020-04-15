package com.bugmakers.jarvistime.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import com.bugmakers.jarvistime.presentation.extensions.string

internal class StringResource(
    private val messageText: String? = null,
    @StringRes private val messageResId: Int? = null
) {
    fun message(context: Context) = messageText ?: {
        messageResId?.let { context.string(it) } ?: ""
    }.invoke()
}

internal inline val String?.asStringResources : StringResource
    get() = StringResource(messageText = this ?: "")

internal inline val Int.asStringResources : StringResource
    get() = StringResource(messageResId = this)