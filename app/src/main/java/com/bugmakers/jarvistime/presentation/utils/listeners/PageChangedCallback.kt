package com.bugmakers.jarvistime.presentation.utils.listeners

import androidx.viewpager2.widget.ViewPager2

internal fun getOnPageChangedCallback(
    doOnPageScrollStateChanged: ((state: Int) -> Unit)? = null,
    doOnPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
    doOnPageSelected: ((position: Int) -> Unit)? = null
) = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageScrollStateChanged(state: Int) {
        doOnPageScrollStateChanged?.invoke(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        doOnPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        doOnPageSelected?.invoke(position)
    }
}