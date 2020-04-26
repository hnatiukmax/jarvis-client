package com.bugmakers.jarvistime.presentation.view.taskchoice

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.entity.enums.SizeType

internal class TaskTypeCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    @DrawableRes @ColorRes
    var backgroundRes = android.R.color.holo_blue_light
        set(value) {
            field = value
            val backgroundRes = color(value)
            setCardBackgroundColor(backgroundRes)
        }

    @DrawableRes
    var iconRes = 0
        set(value) {
            field = value
            icon.setImageResource(field)
        }

    @StringRes var titleRes = 0

    private lateinit var icon: AppCompatImageView
    private lateinit var title: AppCompatTextView

    private val defaultRetreat = dimen(R.dimen.default_margin_small).toInt()
    private val titleWidth: Int
        get() {
            val text = string(titleRes)
            return title.paint.measureText(text).toInt()
        }

    companion object {
        private const val TEXT_APPEARING_DURATION = 200L
        private const val CARD_CORNER = 12f
        private const val CARD_ELEVATION = 10f
        private fun defaultLayoutParams() = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    init {
        layoutParams = defaultLayoutParams()

        radius = dp2Px(CARD_CORNER)
        cardElevation = dp2Px(CARD_ELEVATION)

        initView()
    }

    private fun initView() {
        icon = AppCompatImageView(context).apply {
            setBackgroundResource(iconRes)
        }

        title = AppCompatTextView(context).apply {
            layoutParams = defaultLayoutParams().apply {
                translationY = dp2Px(3f)
                marginStart = defaultRetreat
            }
            maxLines = 1
            setTextAppearance(R.style.AppTheme_Text_Demi_Medium)
            setTextColor(color(R.color.white))
        }

        val linearLayout = LinearLayout(context).apply {
            layoutParams = defaultLayoutParams().apply {
                dividerPadding = defaultRetreat
            }
            addView(icon)
            addView(title)
        }

        val layoutParams = defaultLayoutParams().apply {
            gravity = Gravity.CENTER or Gravity.START
            setMargins(defaultRetreat)
        }

        addView(linearLayout, layoutParams)
    }

    fun expand(duration: Long, onAnimationEnd: (() -> Unit)? = null) {
        val expandedWidth = measuredWidth + titleWidth + defaultRetreat
        animateSize(SizeType.WIDTH,measuredWidth..expandedWidth, duration) {
            title.setText(titleRes)
            title.visibleWithAnimation(true,
                TEXT_APPEARING_DURATION
            ).start()
            onAnimationEnd?.invoke()
        }.start()
    }

    fun collapse(duration: Long, onAnimationEnd: (() -> Unit)? = null) {
        val collapsedWidth = measuredWidth - titleWidth - defaultRetreat
        animateSize(SizeType.WIDTH,measuredWidth..collapsedWidth, duration) {
            title.text = ""
            onAnimationEnd?.invoke()
        }.start()
    }
}