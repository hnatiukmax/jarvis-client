package com.bugmakers.jarvistime.presentation.pages.main.simple_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.DialogDetailTaskInfoBinding

class DetailTaskInfoDialog : DialogFragment() {

    private lateinit var binding: DialogDetailTaskInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_detail_task_info, container, false)

        return binding.getRoot()
    }
}