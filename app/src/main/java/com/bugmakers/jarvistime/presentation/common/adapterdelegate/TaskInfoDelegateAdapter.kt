package com.bugmakers.jarvistime.presentation.common.adapterdelegate

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ItemInboxTaskListBinding
import com.bugmakers.jarvistime.domain.entity.TaskTypeInfo
import com.bugmakers.jarvistime.presentation.utils.provideTaskResInfoByType
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate

internal fun taskInfoDelegateAdapter(
    onTaskClick: (TaskTypeInfo) -> Unit
) = adapterDelegate<TaskTypeInfo, Any>(R.layout.item_inbox_task_list) {

    val binding = ItemInboxTaskListBinding.bind(itemView).apply {
        task.setOnClickListener { onTaskClick(item) }
    }

    bind {
        binding.task.apply {
            provideTaskResInfoByType(context, item.type).let {
                taskIcon = it.iconRes
                taskTitleRes = it.titleRes
                taskDescription = it.descriptionRes
                primaryColorRes = it.primaryColor
                primaryColorLightRes = it.primaryColorLight
            }

            taskProgressCount = item.completedCount to item.allCount
        }
    }
}