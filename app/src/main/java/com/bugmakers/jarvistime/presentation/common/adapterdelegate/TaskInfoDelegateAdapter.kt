package com.bugmakers.jarvistime.presentation.common.adapterdelegate

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ItemInboxTaskListBinding
import com.bugmakers.jarvistime.domain.entity.TaskTypeInfo
import com.bugmakers.jarvistime.presentation.extensions.string
import com.bugmakers.jarvistime.presentation.utils.getTaskResInfoByType
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate

internal fun taskInfoDelegateAdapter(
    onTaskClick: (TaskTypeInfo) -> Unit
) = adapterDelegate<TaskTypeInfo, Any>(R.layout.item_inbox_task_list) {

    val binding = ItemInboxTaskListBinding.bind(itemView).apply {
        task.setOnClickListener { onTaskClick(item) }
    }

    bind {
        binding.task.apply {
            getTaskResInfoByType(context, item.type).apply {
                setBackground(backgroundRes)
                setTaskIcon(iconRes)
                setTaskTitle(string(titleRes))
                setTaskDescription(string(titleRes))
            }

            setTaskCount(string(R.string.task_info, item.completedCount, item.allCount))
        }
    }
}