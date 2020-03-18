package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

internal fun AppCompatActivity.makeToolbarAsActionBar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

internal fun AppCompatActivity.enableBackButton() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
}

internal fun FragmentActivity.hideSoftKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

internal fun AppCompatActivity.showDialog(containerId: Int, dialog: Fragment) {
    supportFragmentManager.commit {
        add(containerId, dialog)
    }
}