package com.bugmakers.jarvistime.presentation.pages.main.simple_card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivitySimpleCardBinding
import com.bugmakers.jarvistime.domain.entity.TypeTask
import java.lang.IllegalArgumentException

class SimpleCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleCardBinding
//    private val type =
//        (intent.getSerializableExtra(ARG_TYPE_TASK) as? TypeTask) ?: throw IllegalArgumentException(
//            "TypeTask argument is absent."
//        )

    companion object {
        private const val ARG_TYPE_TASK = "ARG_TYPE_TASK"

        fun getIntent(context : Context, type : TypeTask) = Intent(context, this::class.java).apply {
            bundleOf(ARG_TYPE_TASK to type)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_card)

        binding.fabAddTask.setOnClickListener {
            DetailTaskInfoDialog().show(supportFragmentManager, DetailTaskInfoDialog.TAG)
        }

        binding.mainToolbar.setLeftActionClickListener {
            onBackPressed()
        }
    }
}