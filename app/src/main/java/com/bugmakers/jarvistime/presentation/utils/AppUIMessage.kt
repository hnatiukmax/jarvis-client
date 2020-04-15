package com.bugmakers.jarvistime.presentation.utils

import android.content.Context
import android.widget.Toast.*
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage.*
import es.dmoral.toasty.Toasty

internal fun Context.showToastMessage(type: TypeUIMessage, content: StringResource) {
    when (type) {
        INFORM -> Toasty.info(this, content.message(this), LENGTH_SHORT, true)
        WARNING -> Toasty.warning(this, content.message(this), LENGTH_SHORT, true)
        ERROR -> Toasty.error(this, content.message(this), LENGTH_SHORT, true)
    }.show()
}