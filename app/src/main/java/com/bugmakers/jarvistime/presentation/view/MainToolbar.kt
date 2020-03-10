package com.bugmakers.jarvistime.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ViewToolbarMainBinding
import com.bugmakers.jarvistime.presentation.extensions.activity
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.string

class MainToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private val binding: ViewToolbarMainBinding

    private var titleTextRes: String? = null
    private var titleTextAppearanceRes = R.style.AppTheme_ToolbarTitle
    private var titleTextColorRes = context.color(R.color.white)
    private var backgroundToolbarColorRes = context.color(android.R.color.transparent)
    private var leftNavigationActionIconRes = 0
    private var rightNavigationActionIconRes = 0

    init {
        setContentInsetsAbsolute(0, 0)

        val layoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_toolbar_main, this, true)

        readAttrs(attrs, defStyleAttr, defStyleRes)
        initToolbar()
    }

    @SuppressLint("NewApi")
    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MainToolbar,
            defStyleAttr,
            defStyleRes
        )

        try {
            attrsArray.apply {
                titleTextRes = getString(R.styleable.MainToolbar_titleText)
                titleTextAppearanceRes = getResourceId(
                    R.styleable.MainToolbar_titleTextAppearance,
                    titleTextAppearanceRes
                )
                titleTextColorRes =
                    getColor(R.styleable.MainToolbar_titleTextColor, titleTextColorRes)
                backgroundToolbarColorRes =
                    getColor(R.styleable.MainToolbar_backgroundColor, backgroundToolbarColorRes)
                leftNavigationActionIconRes = getResourceId(
                    R.styleable.MainToolbar_leftActionIcon,
                    leftNavigationActionIconRes
                )
                rightNavigationActionIconRes = getResourceId(
                    R.styleable.MainToolbar_rightActionIcon,
                    rightNavigationActionIconRes
                )
            }
        } finally {
            attrsArray.recycle()
        }
    }

    private fun initToolbar() {
        setTitleText(titleTextRes)
        setTitleTextAppearance(titleTextAppearanceRes)
        setBackgroundColor(backgroundToolbarColorRes)
        setLeftActionIcon(leftNavigationActionIconRes)
        setRightActionIcon(rightNavigationActionIconRes)
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setToolbarBackgroundColor(@ColorRes colorRes: Int) {
        setBackgroundColor(context.color(colorRes))
    }

    fun setTitleText(@StringRes strRes: Int) {
        if (strRes != 0) {
            setTitleText(context.string(strRes))
        }
    }

    fun setTitleText(titleText: String?) {
        if (!TextUtils.isEmpty(titleText)) {
            binding.title.apply {
                visibility = View.VISIBLE
                text = titleText
            }
        }
    }

    override fun setTitleTextColor(@ColorRes colorRes: Int) {
        if (colorRes != 0) {
            binding.title.setTextColor(colorRes)
        }
    }

    fun setTitleTextAppearance(@StyleRes textAppearanceRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.title.setTextAppearance(textAppearanceRes)
        }
    }

    fun setLeftActionIcon(@DrawableRes iconRes: Int) {
        if (iconRes != 0) {
            binding.leftNavigationAction.apply {
                visibility = View.VISIBLE
                setImageResource(iconRes)
            }
        }
    }

    fun setLeftActionIcon(drawable: Drawable) {
        binding.leftNavigationAction.apply {
            visibility = View.VISIBLE
            setImageDrawable(drawable)
        }
    }

    fun setRightActionIcon(@DrawableRes iconRes: Int) {
        if (iconRes != 0) {
            binding.rightNavigationAction.apply {
                visibility = View.VISIBLE
                setImageResource(iconRes)
            }
        }
    }

    fun setRightActionIcon(drawable: Drawable) {
        binding.rightNavigationAction.apply {
            visibility = View.VISIBLE
            setImageDrawable(drawable)
        }
    }

    fun setLeftActionClickListener(onClickListener: OnClickListener) {
        binding.leftNavigationAction.apply {
            setOnClickListener(null)
            setOnClickListener(onClickListener)
        }
    }

    fun setLeftActionClickListener(onClickListener: () -> Unit) {
        binding.leftNavigationAction.apply {
            setOnClickListener(null)
            setOnClickListener {
                onClickListener.invoke()
            }
        }
    }

    fun setRightActionClickListener(onClickListener: OnClickListener) {
        binding.rightNavigationAction.apply {
            setOnClickListener(null)
            setOnClickListener(onClickListener)
        }
    }

    fun setRightActionClickListener(onClickListener: () -> Unit) {
        binding.rightNavigationAction.apply {
            setOnClickListener(null)
            setOnClickListener {
                onClickListener.invoke()
            }
        }
    }

    fun setLeftActionAsBackButton(isEnabled: Boolean) {
        if (isEnabled) {
            setLeftActionClickListener {
                activity?.onBackPressed()
            }
        } else {
            binding.rightNavigationAction.setOnClickListener(null)
        }
    }
}