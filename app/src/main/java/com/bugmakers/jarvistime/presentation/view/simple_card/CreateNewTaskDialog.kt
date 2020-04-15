package com.bugmakers.jarvistime.presentation.view.simple_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.DialogCreateTaskBinding

class CreateNewTaskDialog : DialogFragment() {

    private lateinit var binding: DialogCreateTaskBinding

    companion object {
        const val TAG = "CreateNewTaskDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CreateTaskDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_create_task, container, false)

        return binding.getRoot()
    }
}