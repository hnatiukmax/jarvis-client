package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

internal interface TaskService {

    @GET("/tasks")
    fun getTasks(): Single<List<TaskResponse>>

    @POST("/create_task")
    fun createTask(@Body taskBody: TaskRequestBody): Completable

    @PUT("/update_task")
    fun updateTask(taskId: Int, @Body taskBody: TaskRequestBody): Completable

    @DELETE("/delete_task")
    fun deleteTask(taskId: Int): Completable
}