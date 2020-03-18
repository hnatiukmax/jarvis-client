package com.bugmakers.jarvistime.presentation.utils

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bugmakers.jarvistime.presentation.utils.TypeUIMessage.*
import es.dmoral.toasty.Toasty

internal fun FragmentActivity.showToastMessage(
    type: TypeUIMessage,
    content: StringResource
) {
    when (type) {
        INFORM -> Toasty.info(this, content.message(this), Toast.LENGTH_SHORT, true)
        WARNING -> Toasty.warning(this, content.message(this), Toast.LENGTH_SHORT, true)
        ERROR -> Toasty.error(this, content.message(this), Toast.LENGTH_SHORT, true)
    }.show()
}