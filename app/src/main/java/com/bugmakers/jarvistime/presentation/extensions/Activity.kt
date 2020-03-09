package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

fun AppCompatActivity.goTo(
    to : Class<out Activity>,
    vararg pairs : Pair<String, Any?> = emptyArray()
) {
    val intent = Intent(this, to).apply {
        bundleOf(*pairs)
    }
    startActivity(intent)
}

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

internal fun AppCompatActivity.showDialog(containerId : Int, dialog : Fragment) {
    supportFragmentManager.commit {
        add(containerId, dialog)
    }
}