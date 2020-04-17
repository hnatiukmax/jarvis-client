package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage
import com.bugmakers.jarvistime.presentation.utils.StringResource
import es.dmoral.toasty.Toasty

fun Context.showToast(
    message : String = this::class.java.simpleName + " toastMessage",
    toastLength : Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, toastLength).show()
}

internal fun Context.makeToastyMessage(type: TypeUIMessage, content: StringResource) {
    when (type) {
        TypeUIMessage.INFORM -> Toasty.info(this, content.message(this), Toast.LENGTH_SHORT, true)
        TypeUIMessage.WARNING -> Toasty.warning(this, content.message(this), Toast.LENGTH_SHORT, true)
        TypeUIMessage.ERROR -> Toasty.error(this, content.message(this), Toast.LENGTH_SHORT, true)
    }.show()
}

fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && this !is LifecycleOwner) {
        curContext = (this as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext
    } else {
        null
    }
}

fun Context.px2Dp(size: Int): Int = (size / Resources.getSystem().displayMetrics.density).toInt()

fun Context.dp2Px(size: Int): Int = (size * Resources.getSystem().displayMetrics.density).toInt()

fun Context.dp2Px(size: Float): Float = size * Resources.getSystem().displayMetrics.density