package com.bugmakers.jarvistime.presentation.pages.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityMainBinding
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.pages.main.simple_card.CreateNewTaskDialog
import com.bugmakers.jarvistime.presentation.pages.main.simple_card.SimpleCardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun getIntent(context : Context) = Intent(context, this::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.fabAddTask.setOnClickListener {
            CreateNewTaskDialog().show(supportFragmentManager, CreateNewTaskDialog.TAG)
        }

        binding.mainToolbar.setLeftActionClickListener {
            binding.dlMain.openDrawer(GravityCompat.START)
        }
        binding.redCard.setOnClickListener(View.OnClickListener {

        })
        binding.blueCard.setOnClickListener(View.OnClickListener {
            goTo(SimpleCardActivity::class.java)
        })
        binding.orangeCard.setOnClickListener(View.OnClickListener {
            goTo(SimpleCardActivity::class.java)
        })
        binding.purpleCard.setOnClickListener(View.OnClickListener {
            goTo(SimpleCardActivity::class.java)
        })
    }
}