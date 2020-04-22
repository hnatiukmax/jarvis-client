package com.bugmakers.jarvistime.presentation.pages.home.inbox

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentInboxBinding
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.domain.entity.TaskTypeInfo
import com.bugmakers.jarvistime.presentation.base.BaseFragment
import com.bugmakers.jarvistime.presentation.common.adapterdelegate.taskInfoDelegateAdapter
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.extensions.showToasty
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

internal class InboxFragment : BaseFragment<FragmentInboxBinding, InboxFragmentViewModel>() {

    override val layoutId = R.layout.fragment_inbox
    override val viewModel: InboxFragmentViewModel by kodeinViewModel()

    private val taskTypeListAdapter by lazy {
        ListDelegationAdapter(taskInfoDelegateAdapter(this::onTaskCardClick))
    }

    companion object {
        fun newInstance() = InboxFragment()
    }

    override fun FragmentInboxBinding.initView() {
        viewModel = this@InboxFragment.viewModel
        initInbox()
    }

    override fun InboxFragmentViewModel.observeViewModel() {
        val testList = mutableListOf<TaskTypeInfo>().also {
            it.add(TaskTypeInfo(type = TaskType.DO_FIRST, allCount = 5, completedCount = 2))
            it.add(TaskTypeInfo(type = TaskType.DO_LATER, allCount = 5, completedCount = 1))
            it.add(TaskTypeInfo(type = TaskType.DO_BY_OTHERS, allCount = 4, completedCount = 3))
            it.add(TaskTypeInfo(type = TaskType.DO_NOT_DO, allCount = 2, completedCount = 0))
        }

        taskTypeInfoList.observe {
            taskTypeListAdapter.items = it
            taskTypeListAdapter.notifyDataSetChanged()
        }
    }

    private fun FragmentInboxBinding.initInbox() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        inbox.apply {
            layoutManager = gridLayoutManager
            adapter = taskTypeListAdapter
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun onTaskCardClick(task: TaskTypeInfo) {
        requireContext().showToasty(TypeUIMessage.INFORM, task.allCount.toString().asStringResources)
    }
}
