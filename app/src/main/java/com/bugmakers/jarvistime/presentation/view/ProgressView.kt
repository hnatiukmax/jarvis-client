package com.bugmakers.jarvistime.presentation.view

import android.annotation.SuppressLint
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

internal class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val progressView: LottieAnimationView

    @DrawableRes
    @ColorRes
    private var backgroundResourcesId = R.color.backgroundProgressView
    private var progressViewPath = "lottiefiles/progress.json"

    init {
        readAttrs(attrs, defStyleAttr, defStyleRes)

        setBackgroundResource(backgroundResourcesId)
        isClickable = false

        progressView = createAnimatedView(progressViewPath)
        addView(
            progressView,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        )
    }

    @SuppressLint("NewApi")
    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressView,
            defStyleAttr,
            defStyleRes
        )

        try {
            attrsArray.apply {
                backgroundResourcesId =
                    getResourceId(R.styleable.ProgressView_background, backgroundResourcesId)
                progressViewPath =
                    getString(R.styleable.ProgressView_progressViewPath) ?: progressViewPath
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
            loop(true)
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
        visibility = View.VISIBLE
        progressView.playAnimation()
    }

    fun hideProgress() {
        visibility = View.GONE
        progressView.cancelAnimation()
    }
}