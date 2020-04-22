package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.mapper.mapToTaskDataSource
import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import com.bugmakers.jarvistime.data.network.response.toTaskTypeInfo
import com.bugmakers.jarvistime.data.service.TaskApiService
import com.bugmakers.jarvistime.domain.entity.Task
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.domain.entity.TaskTypeInfo
import com.bugmakers.jarvistime.presentation.common.rxjava.multiThreads
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class TaskRepository(
    private val taskApiService: TaskApiService
) {

    fun tasksTypeInfo(): Single<List<TaskTypeInfo>> {
        return taskApiService.tasksTypeInfo()
            .multiThreads()
            .map { it.map {
                list -> list.toTaskTypeInfo()
            }}
    }

    fun tasks(): Single<List<TaskResponse>> {
        return taskApiService.tasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTask(taskType: TaskType, description: String): Completable {
        val taskRequestBody = TaskRequestBody(
            taskType = taskType.intValue,
            description = description,
            isCompleted = false
        )

        return taskApiService.createTask(taskRequestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTask(task : Task): Completable {
        return taskApiService.updateTask(task.id, task.mapToTaskDataSource())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTask(requiredTaskId: Int) : Completable {
        return taskApiService.deleteTask(requiredTaskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}