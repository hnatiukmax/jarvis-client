package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.authorization.ApiHeaders
import com.bugmakers.jarvistime.data.network.requests.TaskRequestBody
import com.bugmakers.jarvistime.data.network.response.TaskResponse
import com.bugmakers.jarvistime.data.network.response.TaskTypeInfoResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

internal interface TaskApiService {

    @Headers(ApiHeaders.AUTHORIZATION_TOKEN)
    @GET("/task/tasks-info")
    fun tasksTypeInfo(): Single<List<TaskTypeInfoResponse>>

    @GET("/task/tasks")
    fun tasks(): Single<List<TaskResponse>>

    @POST("/task/task-create")
    fun createTask(@Body taskBody: TaskRequestBody): Completable

    @PUT("/task/task-update/{id}")
    fun updateTask(@Path("id") taskId: Int, @Body taskBody: TaskRequestBody): Completable

    @DELETE("/task/delete_task/{id}")
    fun deleteTask(@Path("id") taskId: Int): Completable
}