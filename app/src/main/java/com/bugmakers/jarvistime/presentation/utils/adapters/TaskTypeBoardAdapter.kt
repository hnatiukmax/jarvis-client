package com.bugmakers.jarvistime.presentation.utils.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bugmakers.jarvistime.presentation.entity.TaskTypeCardInfo
import com.bugmakers.jarvistime.presentation.utils.diffutils.TaskTypeCardInfoDiffCallback
import com.bugmakers.jarvistime.presentation.view.TaskInfoView
import com.bugmakers.jarvistime.presentation.view.taskchoice.TaskTypeCard

internal class TaskTypeBoardAdapter : ListAdapter<TaskTypeCardInfo, TaskTypeBoardAdapter.TaskTypeCardInfoViewHolder>(TaskTypeCardInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskTypeCardInfoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskTypeCardInfoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class TaskTypeCardInfoViewHolder private constructor(view : TaskInfoView): RecyclerView.ViewHolder(view) {

        fun bind(item : TaskTypeCard) {

        }

        companion object {
            fun from(parent: ViewGroup) {

            }
        }

    }
}