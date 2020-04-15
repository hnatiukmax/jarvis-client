package com.bugmakers.jarvistime.presentation.view.simple_card

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

    companion object {
        const val TAG = "DetailTaskInfoDialog"
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
            DataBindingUtil.inflate(inflater, R.layout.dialog_detail_task_info, container, false)

        return binding.getRoot()
    }
}