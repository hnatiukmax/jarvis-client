package com.bugmakers.jarvistime.presentation.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.bugmakers.jarvistime.presentation.entity.TaskTypeCardInfo

internal class TaskTypeCardInfoDiffCallback : DiffUtil.ItemCallback<TaskTypeCardInfo>() {

    override fun areItemsTheSame(oldItem: TaskTypeCardInfo, newItem: TaskTypeCardInfo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: TaskTypeCardInfo, newItem: TaskTypeCardInfo): Boolean {
        return oldItem == newItem
    }
}