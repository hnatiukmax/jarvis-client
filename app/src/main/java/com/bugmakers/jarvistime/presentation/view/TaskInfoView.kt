package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ViewTaskInfoBinding
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.dp2Px

internal class TaskInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    @DrawableRes
    var taskIcon = 0
        set(value) {
            field = value
            binding.iconTask.setBackgroundResource(value)
        }

    @StringRes
    var taskTitleRes = 0
        set(value) {
            field = value
            binding.taskTitle.text = string(value)
        }

    @StringRes
    var taskDescription = 0
        set(value) {
            field = value
            binding.taskDescription.text = string(value)
        }

    @ColorRes
    var primaryColorRes = R.color.background_task_do_first
        set(value) {
            field = value
            updateColor()
        }

    @ColorRes
    var primaryColorLightRes = R.color.background_task_do_first_light
        set(value) {
            field = value
            updateColor()
        }

    var taskProgressCount = 0 to 0
        set(value) {
            field = value
            updateTaskProgress()
        }

    private val binding: ViewTaskInfoBinding

    companion object {
        private const val CORNER_RADIUS = 6
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_task_info, this, true)

        initView()
    }

    private fun initView() {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        radius = dp2Px(CORNER_RADIUS).toFloat()
        cardElevation = dp2Px(5f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)

        children.forEach {
            it.measure(
                MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
            )
        }
    }

    private fun updateColor() {
        binding.apply {
            taskProgress.apply {
                allColor = primaryColorLightRes
                selectedColor = primaryColorRes
            }

            taskTitle.setTextColor(color(primaryColorRes))
            taskDescription.setTextColor(color(primaryColorRes))
            iconTask.setBackgroundTint(color(primaryColorRes))
            topBackground.setBackgroundColor(color(primaryColorLightRes))
            taskCount.setTextColor(color(primaryColorRes))
        }
    }

    private fun updateTaskProgress() {
        binding.apply {
            taskProgress.apply {
                allCount = taskProgressCount.second
                selectedCount = taskProgressCount.first
            }

            taskCount.text = string(R.string.task_count_info, taskProgressCount.first, taskProgressCount.second)
        }
    }
}