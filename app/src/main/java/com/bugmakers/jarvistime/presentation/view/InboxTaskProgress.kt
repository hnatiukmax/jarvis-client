package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.ColorRes
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.dp2Px
import com.bugmakers.jarvistime.presentation.extensions.tryElse

internal class InboxTaskProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var selectedCount = 0
        set(value) {
            field = value
            invalidate()
        }

    var allCount = 0
        set(value) {
            field = value
            invalidate()
        }

    @ColorRes
    var selectedColor = R.color.background_task_do_first
        set(value) {
            field = value
            invalidate()
        }

    @ColorRes
    var allColor = R.color.background_task_do_first_light
        set(value) {
            field = value
            invalidate()
        }

    /* Paint */
    private lateinit var allPaint: Paint
    private lateinit var selectedPaint: Paint

    /* Rect */
    private lateinit var allRect: RectF
    private lateinit var selectedRect: RectF

    companion object {
        private const val PROGRESS_HEIGHT = WRAP_CONTENT
        private const val PROGRESS_WIDTH = MATCH_PARENT
    }

    init {
        layoutParams = ViewGroup.LayoutParams(PROGRESS_HEIGHT, PROGRESS_WIDTH)

        readAttrs(attrs, defStyleAttr, defStyleRes)

        initPaints()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        initRect()

        canvas?.apply {
            drawProgress(this)
        }
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.InboxTaskProgress, defStyleAttr, defStyleRes)

        try {
            selectedColor = attrsArray.getResourceId(R.styleable.InboxTaskProgress_itp_selectedColor, selectedColor)
            allColor = attrsArray.getResourceId(R.styleable.InboxTaskProgress_itp_allColor, allColor)

            selectedCount = attrsArray.getInt(R.styleable.InboxTaskProgress_itp_selectedCount, selectedCount)
            allCount = attrsArray.getInt(R.styleable.InboxTaskProgress_itp_allCount, allCount)

        } finally {
            attrsArray.recycle()
        }
    }

    private fun initPaints() {
        allPaint = Paint().apply {
            style = Paint.Style.FILL
            color = color(allColor)
        }

        selectedPaint = Paint().apply {
            style = Paint.Style.FILL
            color = color(selectedColor)
        }
    }

    private fun initRect() {
        val selectedRectWidth = tryElse {
            selectedCount * measuredWidth / allCount
        } ?: 0

        allRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        selectedRect = RectF(0f, 0f, selectedRectWidth.toFloat(), measuredHeight.toFloat())
    }

    private fun drawProgress(canvas: Canvas) {
        selectedPaint.color = color(selectedColor)
        allPaint.color = color(allColor)

        canvas.apply {
            val cornerRadius = dp2Px(5).toFloat()

            drawRoundRect(allRect, cornerRadius, cornerRadius, allPaint)
            drawRoundRect(selectedRect, cornerRadius, cornerRadius, selectedPaint)
        }
    }
}