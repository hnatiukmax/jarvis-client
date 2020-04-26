package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType.*
import java.lang.Exception

internal fun AppCompatActivity.makeToolbarAsActionBar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

internal fun AppCompatActivity.enableBackButton() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
}

internal val AppCompatActivity.fragmentCount: Int
    get() = supportFragmentManager.fragments.size

internal fun AppCompatActivity.fragmentAt(index: Int): Fragment? {
    return try {
        supportFragmentManager.fragments[index]
    } catch (ex: Exception) {
        null
    }
}

internal fun FragmentActivity.fragmentByTag(tag: String) : Fragment? {
    return supportFragmentManager.findFragmentByTag(tag)
}

internal fun FragmentActivity.hideSoftKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

internal fun Activity.goTo(
    to: Class<out Activity>,
    animationType: AnimationType? = null,
    bundle: Bundle? = null,
    close: Boolean = false
) {
    val intent = Intent(this, to).apply {
        putExtras(bundle ?: return@apply)
    }

    startActivity(intent)
    makeActivityTransition(animationType ?: return)

    if (close) {
        finish()
    }
}

internal fun Activity.closeWithTransition(animationType: AnimationType) {
    onBackPressed()
    makeActivityTransition(animationType)
}

internal fun Activity.makeActivityTransition(animationType: AnimationType) {
    val animationRes = when (animationType) {
        SLIDE_UP -> R.anim.slide_up_enter to R.anim.empty
        SLIDE_RIGHT -> R.anim.slide_in_left to R.anim.slide_out_right
        SLIDE_DOWN -> 0 to R.anim.slide_down_exit
        SLIDE_LEFT -> R.anim.slide_left_enter to R.anim.slide_left_exit
        FADE -> R.anim.fade_exit to R.anim.fade_enter
    }

    overridePendingTransition(animationRes.first, animationRes.second)
}