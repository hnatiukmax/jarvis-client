package com.bugmakers.jarvistime.presentation.pages.home.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.bugmakers.jarvistime.data.repository.TaskRepository
import com.bugmakers.jarvistime.data.repository.UserRepository
import com.bugmakers.jarvistime.domain.entity.TaskTypeInfo
import com.bugmakers.jarvistime.domain.entity.UserInfo
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.common.rxjava.subscribe
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.utils.delegatedLiveData

internal class InboxFragmentViewModel(
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    val userInfo by delegatedLiveData<UserInfo>(::loadUserInfo)

    val taskTypeInfoList by delegatedLiveData<List<TaskTypeInfo>>(::loadTaskTypeInfoList)

    val allCount: LiveData<Int> = map(taskTypeInfoList) {
        it.sumBy { task -> task.allCount }
    }

    private fun loadUserInfo() {
        compositeDisposable plus userRepository.getUserInfo()
            .enableProgress()
            .handleError()
            .subscribe(
                onSuccess = { userInfo.value = it }
            )
    }

    private fun loadTaskTypeInfoList() {
        compositeDisposable plus taskRepository.tasksTypeInfo()
            .enableProgress()
            .handleError()
            .subscribe(
                onSuccess = { taskTypeInfoList.value = it }
            )
    }
}