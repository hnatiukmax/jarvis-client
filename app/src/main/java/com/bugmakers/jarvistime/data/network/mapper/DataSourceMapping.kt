package com.bugmakers.jarvistime.data.network.mapper

import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import com.bugmakers.jarvistime.data.network.response.UserResponse
import com.bugmakers.jarvistime.domain.entity.Task
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.domain.entity.User

internal fun User.mapToUserDataSource() = UserRequestBody(
    username = username,
    password = password
)

internal fun Task.mapToTaskDataSource() = TaskRequestBody(
    taskType = type.intValue,
    description = description,
    isCompleted = isCompleted
)

internal fun UserResponse.mapToDomainUser() = User(
    id = id,
    username = username,
    password = password
)

internal fun TaskResponse.mapToDomainTask() = Task(
    id = id,
    type = TaskType.typeById(type),
    description = description,
    isCompleted = isCompleted
)