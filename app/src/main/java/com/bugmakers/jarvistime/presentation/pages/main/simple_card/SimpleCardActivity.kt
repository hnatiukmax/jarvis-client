package com.bugmakers.jarvistime.presentation.pages.main.simple_card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityMainBinding

class SimpleCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun getIntent(context : Context) = Intent(context, this::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_card)
    }
}