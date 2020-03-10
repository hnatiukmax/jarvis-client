package com.bugmakers.jarvistime.presentation.pages.main.side_menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.NavigationMenuLayoutBinding

class NavigationMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: NavigationMenuLayoutBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.navigation_menu_layout, this, true)
        initNavigationView()
    }

    private fun initNavigationView(){
        binding.btnLogout.setOnClickListener(OnClickListener {
            Toast.makeText(context, "logout", Toast.LENGTH_SHORT).show()
        })
    }

}