package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskTypeInfoResponse
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

internal interface TaskService {

    @GET("/tasks-info")
    fun tasksTypeInfo(): Single<List<TaskTypeInfoResponse>>

    @GET("/tasks")
    fun tasks(): Single<List<TaskResponse>>

    @POST("/task-create")
    fun createTask(@Body taskBody: TaskRequestBody): Completable

    @PUT("/task-update/{id}")
    fun updateTask(@Path("id") taskId: Int, @Body taskBody: TaskRequestBody): Completable

    @DELETE("/delete_task/{id}")
    fun deleteTask(@Path("id") taskId: Int): Completable
}