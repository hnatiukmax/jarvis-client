package com.bugmakers.jarvistime.presentation.view

import android.animation.ArgbEvaluator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.dimen
import com.bugmakers.jarvistime.presentation.extensions.dp2Px
import com.bugmakers.jarvistime.presentation.extensions.setBackgroundTint
import com.bugmakers.jarvistime.presentation.entity.enums.SizeType

open class PageIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    var countItem = 0
        set(value) {
            field = value
            updateCountItems()
        }
    var selectedItem = 0
        set(value) {
            prevSelected = field
            field = value
            updateSelectedItems()
        }

    private var prevSelected = 0
    private var itemSpacing = dimen(R.dimen.default_margin_small)
    @ColorRes private var selectedColorRes = R.color.main_button_new
    @ColorRes private var unselectedColorRes = R.color.page_indicator_unselected
    @DrawableRes private var unselectedItemRes = R.drawable.page_indicator_unselected

    private val argbEvaluator by lazy { ArgbEvaluator() }

    companion object {
        private const val ITEM_CHANGE_SELECTED_DURATION = 200L
        private const val ITEM_HEIGHT = 4
        private const val ITEM_UNSELECTED_WIDTH = 15
        private const val ITEM_SELECTED_WIDTH = 25
    }

    init {
        orientation = HORIZONTAL
        weightSum = countItem.toFloat()

        readAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorView, defStyleAttr, defStyleRes)

        try {
            countItem = attrsArray.getInteger(R.styleable.PageIndicatorView_piv_count, countItem)
            itemSpacing = attrsArray.getDimension(R.styleable.PageIndicatorView_piv_spacing, itemSpacing)
        } finally {
            attrsArray.recycle()
        }
    }

    private fun updateCountItems() {
        val (itemWidth, itemHeight) = dp2Px(ITEM_UNSELECTED_WIDTH) to dp2Px(ITEM_HEIGHT)

        repeat(countItem) {
            val layoutParams = LayoutParams(itemWidth, itemHeight).apply {
                if (countItem - childCount != 1) {
                    marginEnd = itemSpacing.toInt()
                }
                gravity = Gravity.CENTER_VERTICAL
            }

            val view = createItem()
            addView(view, layoutParams)
        }
    }

    private fun createItem() = View(context).apply {
        setBackgroundResource(unselectedItemRes)
    }

    private fun updateSelectedItems() {
        val unselectedItem = getChildAt(prevSelected)
        val selectedItem = getChildAt(selectedItem)

        val colorAnimation = animateByUpdateListener(0.0f..1.0f, ITEM_CHANGE_SELECTED_DURATION) {
            val selectedColor = argbEvaluator.evaluate(it, color(unselectedColorRes), color(selectedColorRes)) as Int
            val unselectedColor = argbEvaluator.evaluate(it, color(selectedColorRes), color(unselectedColorRes)) as Int


            unselectedItem.setBackgroundTint(unselectedColor)
            selectedItem.setBackgroundTint(selectedColor)
        }


        val (unselectedWidth, selectedWidth) = dp2Px(ITEM_UNSELECTED_WIDTH) to dp2Px(ITEM_SELECTED_WIDTH)

        val unselectedItemWidthAnimation = unselectedItem.animateSize(
            sizeType = SizeType.WIDTH,
            range = selectedWidth..unselectedWidth,
            duration = ITEM_CHANGE_SELECTED_DURATION / 2
        )

        val selectedItemWidthAnimation = selectedItem.animateSize(
            sizeType = SizeType.WIDTH,
            range = unselectedWidth..selectedWidth,
            duration = ITEM_CHANGE_SELECTED_DURATION
        )

        colorAnimation.start()
        unselectedItemWidthAnimation.start()
        selectedItemWidthAnimation.start()
    }
}