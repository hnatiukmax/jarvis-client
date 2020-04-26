package com.bugmakers.jarvistime.presentation.view

import android.content.Context
import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ViewInputEditTextBinding
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.extensions.showKeyboard
import com.bugmakers.jarvistime.presentation.extensions.visibleWithAnimation
import com.bugmakers.jarvistime.presentation.utils.listeners.SimpleTextWatcher

internal class InputEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var text: String
        get() = "${binding.text.text}"
        set(value) {
            binding.text.setText(value)
        }

    var mHasError = false
    var onTextChangedListener: ((String) -> Unit)? = null

    private val binding: ViewInputEditTextBinding

    @DrawableRes
    private var toggleIconRes: Int? = null
    private var hintText: String? = null
    private var imeOptions = EditorInfo.IME_ACTION_NONE
    private var inputType = TYPE_CLASS_TEXT
    private var isToggleVisible = false
    private var isToggleEnable = false
    private var isValidationVisible = false

    companion object {
        private const val ALPHA_DURATION = 300L
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_input_edit_text, this, true)

        readAttrs(attrs, defStyleAttr, defStyleRes)

        initView()
    }

    /**
     * Show keyboard and request layout to EditText
     */
    fun activate() {
        binding.text.showKeyboard()
    }

    fun hasError(isError: Boolean) {
        if (!isValidationVisible || !mHasError && !isError) {
            return
        }

        binding.validationCheckmark.apply {
            if (mHasError == isError && !isError) {
                return
            }

            binding.validationCheckmark.apply {
                if (isError) {
                    setBackgroundResource(R.drawable.ic_error_mark)
                }
                visibleWithAnimation(true, ALPHA_DURATION).start()
            }
        }

        mHasError = isError
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.InputEditTextView, defStyleAttr, defStyleRes)

        try {
            hintText = attrsArray.getString(R.styleable.InputEditTextView_iet_hint)
            imeOptions = attrsArray.getInt(R.styleable.InputEditTextView_android_imeOptions, imeOptions)
            inputType = attrsArray.getInt(R.styleable.InputEditTextView_android_inputType, inputType)

            if (attrsArray.hasValue(R.styleable.InputEditTextView_iet_toggleIcon)) {
                toggleIconRes = attrsArray.getResourceId(R.styleable.InputEditTextView_iet_toggleIcon, 0)
            }
            isToggleVisible = attrsArray.getBoolean(R.styleable.InputEditTextView_iet_toggleVisible, isToggleVisible)
            isToggleEnable = attrsArray.getBoolean(R.styleable.InputEditTextView_iet_toggleEnabled, isToggleEnable)
            isValidationVisible = attrsArray.getBoolean(R.styleable.InputEditTextView_iet_validationVisible, isValidationVisible)
        } finally {
            attrsArray.recycle()
        }
    }

    private fun initView() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setBackgroundResource(R.drawable.background_input_edit_text)

        binding.apply {
            toggle.let {
                it.isVisible = isToggleVisible
                it.isClickable = isToggleEnable
                it.setBackgroundResource(toggleIconRes ?: return@let)
            }

            configEditText()
        }
    }

    private fun configEditText() {
        binding.text.apply {
            val currentTypeface = typeface

            hint = hintText
            imeOptions = imeOptions
            inputType = inputType
            typeface = currentTypeface

            addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    onTextChangedListener?.invoke(s.toString())
                }
            })

            onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                (activity as? OnInputActivatedListener)?.onInputActivated(
                    this@InputEditTextView,
                    hasFocus
                )
            }
        }
    }

    interface OnInputActivatedListener {

        fun onInputActivated(view: InputEditTextView, isActivated: Boolean)
    }
}