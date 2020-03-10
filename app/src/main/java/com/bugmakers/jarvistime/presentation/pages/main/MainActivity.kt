package com.bugmakers.jarvistime.presentation.pages.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addFragmentWitoutBackStack(MatrixFragment(), MatrixFragment.TAG)
//        binding.mainToolbar.setLeftActionAsBackButton(true)
    }

    fun addFragmentWitoutBackStack(fragment : Fragment, tag : String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, tag)
            .commit()
    }

    fun addFragment(fragment : Fragment, tag : String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun replaceFragment(fragment : Fragment, tag : String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }
}