package com.bugmakers.jarvistime.presentation.view.taskchoice

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.presentation.extensions.alphaWithAnimation
import com.bugmakers.jarvistime.presentation.extensions.dimen
import com.bugmakers.jarvistime.presentation.extensions.dp2Px

internal class TaskChoiceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onTaskSelectedListener: ((TaskType) -> Unit)? = null
    var selectedTaskIndex = -1

    private val defaultRetreat = dimen(R.dimen.default_margin_small).toInt()
    private val iconSize = dp2Px(24)
    private var isAnimatedNow = false

    companion object {
        private const val ITEM_COUNT = 4
        private const val EXPANDED_DURATION = 500L
        private const val COLLAPSED_DURATION = 500L
        private const val DEFAULT_UNSELECTED_ALPHA = 0.5f
    }

    init {
        orientation = HORIZONTAL
        weightSum = ITEM_COUNT.toFloat()

        initView()
    }

    @SuppressLint("Recycle")
    private fun initView() {
        val backgroundResArray = resources.obtainTypedArray(R.array.task_type_primary)
        val iconResArray = resources.obtainTypedArray(R.array.task_type_icon)
        val descriptionResArray = resources.obtainTypedArray(R.array.task_type_title)

        if (backgroundResArray.length() != ITEM_COUNT ||
            iconResArray.length() != ITEM_COUNT ||
            descriptionResArray.length() != ITEM_COUNT
        ) {
            return
        }

        repeat(ITEM_COUNT) {
            val backgroundRes = backgroundResArray.getResourceId(it, 0)
            val iconRes = iconResArray.getResourceId(it, 0)
            val descriptionRes = descriptionResArray.getResourceId(it, 0)

            val item = createItem(it, backgroundRes, iconRes, descriptionRes)

            val size = iconSize + 2 * defaultRetreat
            val layoutParams = LayoutParams(size, size).apply {
                weight = 1f
                if (it < ITEM_COUNT - 1) {
                    marginEnd = dimen(R.dimen.default_margin_large).toInt()
                }
            }

            addView(item, layoutParams)
        }
    }

    private fun createItem(
        index: Int,
        @ColorRes backgroundRes: Int,
        @DrawableRes iconRes: Int,
        @StringRes descriptionRes: Int
    ) = TaskTypeCard(context).apply {
        alpha = DEFAULT_UNSELECTED_ALPHA

        this.backgroundRes = backgroundRes
        this.iconRes = iconRes
        this.titleRes = descriptionRes

        setOnClickListener {
            if (isAnimatedNow || index == selectedTaskIndex) {
                return@setOnClickListener
            }

            onTaskSelectedListener?.invoke(TaskType.typeById(index + 1))

            isAnimatedNow = true

            val setSelected = {
                alphaWithAnimation(DEFAULT_UNSELECTED_ALPHA..1f, EXPANDED_DURATION).start()
                expand(EXPANDED_DURATION) {
                    isAnimatedNow = false
                    selectedTaskIndex = index
                }
            }

            if (selectedTaskIndex >= 0) {
                (this@TaskChoiceView.getChildAt(selectedTaskIndex) as TaskTypeCard).apply {
                    alphaWithAnimation(1f..DEFAULT_UNSELECTED_ALPHA, COLLAPSED_DURATION).start()
                    collapse(COLLAPSED_DURATION)
                    setSelected()
                }
            } else {
                setSelected()
            }
        }
    }
}