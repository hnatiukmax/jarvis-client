package com.bugmakers.jarvistime.presentation.view.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.DialogInfoBinding
import com.bugmakers.jarvistime.presentation.entity.AppUIMessage
import com.bugmakers.jarvistime.presentation.extensions.string
import com.bugmakers.jarvistime.presentation.utils.getIconResByTypeMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class InfoDialog private constructor() : BottomSheetDialogFragment() {

    private lateinit var binding: DialogInfoBinding

    private var onDismissListener: OnDismissListener? = null

    companion object {
        private const val ARG_ICON = "ARG_ICON"
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"

        fun newInstance(
            context: Context,
            @DrawableRes iconRes: Int,
            @StringRes titleRes: Int,
            @StringRes descriptionRes: Int
        ) = newInstance(
            iconRes = iconRes,
            title = context.string(titleRes),
            description = context.string(descriptionRes)
        )

        fun newInstance(
            @DrawableRes iconRes: Int,
            title: String,
            description: String
        ) = InfoDialog().apply {
            arguments = bundleOf(
                ARG_ICON to iconRes,
                ARG_TITLE to title,
                ARG_DESCRIPTION to description
            )
        }

        fun newInstance(context: Context, appUIMessage: AppUIMessage) = InfoDialog().apply {
            arguments = bundleOf(
                ARG_ICON to getIconResByTypeMessage(appUIMessage.typeMessage),
                ARG_DESCRIPTION to appUIMessage.stringResource.message(context)
            )
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent is OnDismissListener) {
            onDismissListener = parent
        } else if (context is OnDismissListener) {
            onDismissListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_info, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadConfigFromBundle()
    }

    private fun loadConfigFromBundle() {
        arguments?.let {
            binding.apply {
                icon.setBackgroundResource(it.getInt(ARG_ICON))

                title.isVisible = it.containsKey(ARG_TITLE)
                title.text = it.getString(ARG_TITLE)

                description.text = it.getString(ARG_DESCRIPTION)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDismissListener?.onDismissListener(tag)
    }

    class Builder(private val context: Context) {
        @DrawableRes
        private var iconRes: Int? = null
        private var title: String? = null
        private var description: String? = null

        private var onDismissListener: OnDismissListener? = null

        fun setIconResource(@DrawableRes iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun setTitleResource(@StringRes titleRes: Int): Builder {
            this.title = context.string(titleRes)
            return this
        }

        fun setDescriptionResource(@StringRes descriptionRes: Int): Builder {
            this.description = context.string(descriptionRes)
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setOnDismissListener(onDismissListener: OnDismissListener): Builder {
            this.onDismissListener = onDismissListener
            return this
        }

        fun setOnDismissListener(callback: (tag: String?) -> Unit): Builder {
            this.onDismissListener = object : OnDismissListener {
                override fun onDismissListener(tag: String?) {
                    callback(tag)
                }
            }
            return this
        }

        fun build(): InfoDialog {
            val args = bundleOf(
                ARG_ICON to iconRes,
                ARG_TITLE to title,
                ARG_DESCRIPTION to description
            )

            return InfoDialog().apply {
                arguments = args
                this@Builder.onDismissListener?.let {
                    onDismissListener = it
                }
            }
        }
    }

    interface OnDismissListener {
        fun onDismissListener(tag: String?)
    }
}