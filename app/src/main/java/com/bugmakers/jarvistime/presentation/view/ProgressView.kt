package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.visibleWithAnimation

internal class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val progressView: LottieAnimationView

    companion object {
        private const val APPEARING_DURATION = 300L
    }

    @DrawableRes
    @ColorRes
    private var backgroundResourcesId = R.drawable.background_progress_view
    private var progressViewPath = "lottiefiles/progress.json"

    init {
        readAttrs(attrs, defStyleAttr, defStyleRes)

        setBackgroundResource(backgroundResourcesId)
        isClickable = true
        isFocusable = true

        progressView = createAnimatedView(progressViewPath)

        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        addView(progressView, layoutParams)
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, defStyleRes)

        try {
            attrsArray.apply {
                backgroundResourcesId = getResourceId(R.styleable.ProgressView_background, backgroundResourcesId)
                progressViewPath = getString(R.styleable.ProgressView_progressViewPath) ?: progressViewPath
            }
        } finally {
            attrsArray.recycle()
        }
    }

    private fun createAnimatedView(lottieAnimationPath: String) =
        LottieAnimationView(context).apply {
            repeatCount = Int.MAX_VALUE
            repeatMode = LottieDrawable.RESTART
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setAnimation(lottieAnimationPath)
        }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE) {
            progressView.playAnimation()
        } else {
            progressView.progress = 0f
            progressView.pauseAnimation()
        }
    }

    fun showProgress() {
        progressView.playAnimation()
        visibleWithAnimation(true, APPEARING_DURATION)
    }

    fun hideProgress() {
        progressView.pauseAnimation()
        visibleWithAnimation(false, APPEARING_DURATION)
    }
}