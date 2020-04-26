package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.children
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationState.*
import com.bugmakers.jarvistime.presentation.entity.enums.NavigationPage
import com.bugmakers.jarvistime.presentation.extensions.childAt
import com.bugmakers.jarvistime.presentation.extensions.dp2Px
import com.bugmakers.jarvistime.presentation.extensions.setDrawableTintWithAnimation
import com.bugmakers.jarvistime.presentation.utils.animate

internal class BottomNavigationMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    var selectedMenuItem = -1
        private set

    var state = EXPANDED

    var onMenuItemClickListener: OnMenuItemClickListener? = null


    private val itemsRes = mutableSetOf<Pair<Int, NavigationPage>>()

    private var lastMeasuredWidth = 0

    @DrawableRes
    @ColorRes
    private var defaultBackgroundRes = R.drawable.background_bottom_menu
    @ColorRes
    private var unselectedColor = R.color.gray
    @ColorRes
    private var selectedColor = R.color.main_green

    private val onTouchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (childCount == 0) return@OnTouchListener false

            val itemPosition = (event.x / (measuredWidth / childCount)).toInt()

            when {
                state == COLLAPSED -> expand()
                itemPosition == childCount - 1 -> collapse()
                else -> changeSelectedMenuItem(itemPosition)
            }
        }
        true
    }

    companion object {
        // Menu constants
        private const val MENU_HEIGHT = 56
        private const val COLLAPSE_DURATION = 600L
        private const val ITEMS_APPEARANCE_DURATION = 150L

        // Menu item constants
        private const val ITEM_MARGIN = 27
        private const val ITEM_SCALE = 1.2f
        private const val SELECTED_DURATION = 200L
        private const val UNSELECTED_DURATION = 150L
    }

    init {
        layoutParams = ViewGroup.LayoutParams(dp2Px(MENU_HEIGHT), MATCH_PARENT)
        translationZ = dp2Px(2f)
        orientation = HORIZONTAL
        setBackgroundResource(defaultBackgroundRes)

        setOnTouchListener(onTouchListener)
    }

    fun addMenuItems(vararg items: Pair<Int, NavigationPage>) {
        items.forEach {
            itemsRes.add(it)
            placeMenuItem(it.first)
        }

        placeMenuItem(R.drawable.ic_inverse_back)
    }

    private fun placeMenuItem(@DrawableRes iconRes: Int) {
        val size = dp2Px(20)

        val item = createItem(iconRes).apply {
            layoutParams = LayoutParams(size, size).apply {
                rightMargin = dp2Px(ITEM_MARGIN)
                gravity = Gravity.CENTER_VERTICAL


                if (childCount == 0) {
                    leftMargin = dp2Px(ITEM_MARGIN)
                }
            }
        }

        addView(item)

        // set first menu item selected
        if (itemsRes.size == 1) {
            changeSelectedMenuItem(0)
        }
    }

    private fun createItem(@DrawableRes iconRes: Int) =
        AppCompatImageView(context).apply {
            setBackgroundResource(iconRes)
        }

    private fun collapse() {
        state = COLLAPSING
        lastMeasuredWidth = measuredWidth
        gravity = Gravity.END

        animate(this)
            .width(dp2Px(MENU_HEIGHT))
            .horizontalBias(1f)
            .duration(COLLAPSE_DURATION)
            .doOnEnd { state = COLLAPSED }
            .start()

        children.forEach {
            if (it == children.last()) {
                animate(it)
                    .rotate(180f, true)
                    .drawableTint(unselectedColor, selectedColor)
                    .rightMargin(dp2Px(19))
                    .duration(COLLAPSE_DURATION)
                    .start()

                return@forEach
            }

            animate(it).alpha(0f, ITEMS_APPEARANCE_DURATION).start()
        }
    }

    private fun expand() {
        state = EXPANDING

        animate(children.last())
            .rotate(180f, false)
            .drawableTint(selectedColor, unselectedColor)
            .rightMargin(dp2Px(ITEM_MARGIN))
            .duration(COLLAPSE_DURATION)
            .start()

        animate(this)
            .width(lastMeasuredWidth)
            .horizontalBias(0.5f)
            .duration(COLLAPSE_DURATION)
            .doOnEnd {
                children.forEach {
                    if (it == children.last()) return@forEach

                    animate(it).alpha(1f, ITEMS_APPEARANCE_DURATION).start()
                }
                state = EXPANDED
            }
            .start()
    }

    private fun changeSelectedMenuItem(newSelectedMenuItem: Int) {
        if (selectedMenuItem == newSelectedMenuItem) {
            return
        }

        val itemTag = itemsRes.elementAt(newSelectedMenuItem).second
        onMenuItemClickListener?.onMenuItemClick(itemTag)

        childAt<ImageView>(selectedMenuItem)?.apply {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(UNSELECTED_DURATION)
                .start()

            setDrawableTintWithAnimation(
                startColor = selectedColor,
                endColor = unselectedColor,
                duration = UNSELECTED_DURATION
            ).start()
        }

        childAt<ImageView>(newSelectedMenuItem)?.apply {
            animate()
                .scaleX(ITEM_SCALE)
                .scaleY(ITEM_SCALE)
                .setDuration(SELECTED_DURATION)
                .start()

            setDrawableTintWithAnimation(
                startColor = unselectedColor,
                endColor = selectedColor,
                duration = SELECTED_DURATION,
                onAnimationEnd = { selectedMenuItem = newSelectedMenuItem }
            ).start()
        }
    }

    internal interface OnMenuItemClickListener {

        fun onMenuItemClick(page: NavigationPage)
    }
}