package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity

internal fun FragmentActivity.goTo(
    to: Class<out Activity>,
    vararg pairs: Pair<String, Any?> = emptyArray()
) {
    val intent = Intent(this, to).apply {
        bundleOf(*pairs)
    }
    startActivity(intent)
}