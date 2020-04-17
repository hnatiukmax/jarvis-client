package com.bugmakers.jarvistime.presentation.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import miaoyongjun.pagetransformer.MagicTransformer
import miaoyongjun.pagetransformer.TransitionEffect

internal class ViewPagerTransformer(
    effect: TransitionEffect
) : ViewPager2.PageTransformer {

    private val viewPagerTransformer = MagicTransformer.getPageTransformer(effect)

    override fun transformPage(page: View, position: Float) {
        viewPagerTransformer.transformPage(page, position)
    }
}