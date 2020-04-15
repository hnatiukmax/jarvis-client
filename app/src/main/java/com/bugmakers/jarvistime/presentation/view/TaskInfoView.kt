package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ViewTaskInfoBinding
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.dimen
import com.bugmakers.jarvistime.presentation.extensions.dp2Px

internal class TaskInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var backgroundRes = color(android.R.color.holo_blue_bright)
        set(value) {
            setCardBackgroundColor(value)
        }

    private val binding: ViewTaskInfoBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_task_info, this, true)

        initView()
    }

    private fun initView() {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        setCardBackgroundColor(backgroundRes)
        radius = dimen(R.dimen.defaultCornerRadius)
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

    fun setBackground(backgroundRes: Int) {
        setCardBackgroundColor(backgroundRes)
    }

    fun setTaskIcon(@DrawableRes iconRes: Int) {
        binding.iconTask.setImageResource(iconRes)
    }

    fun setTaskCount(count: String) {
        binding.taskCount.text = count
    }

    fun setTaskTitle(title: String) {
        binding.taskTitle.text = title
    }

    fun setTaskDescription(description: String) {
        binding.taskTitle.text = description
    }
}