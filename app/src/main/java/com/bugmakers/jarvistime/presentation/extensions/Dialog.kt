package com.bugmakers.jarvistime.presentation.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal fun BottomSheetDialogFragment.show(activity: FragmentActivity, tag: String? = null) {
    show(activity.supportFragmentManager, tag)
}

internal fun BottomSheetDialogFragment.show(fragment: Fragment, tag: String? = null) {
    show(fragment.parentFragmentManager, tag)
}