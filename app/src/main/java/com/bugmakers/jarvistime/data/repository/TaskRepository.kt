package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.mapper.mapToTaskDataSource
import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import com.bugmakers.jarvistime.data.network.response.TaskTypeInfoResponse
import com.bugmakers.jarvistime.data.service.TaskService
import com.bugmakers.jarvistime.domain.entity.Task
import com.bugmakers.jarvistime.domain.entity.TaskType
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class TaskRepository(
    private val taskService: TaskService
) {

    fun tasksTypeInfo(): Single<List<TaskTypeInfoResponse>> {
        return taskService.tasksTypeInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun tasks(): Single<List<TaskResponse>> {
        return taskService.tasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTask(taskType: TaskType, description: String): Completable {
        val taskRequestBody = TaskRequestBody(
            taskType = taskType.intValue,
            description = description,
            isCompleted = false
        )

        return taskService.createTask(taskRequestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTask(task : Task): Completable {
        return taskService.updateTask(task.id, task.mapToTaskDataSource())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTask(requiredTaskId: Int) : Completable {
        return taskService.deleteTask(requiredTaskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}