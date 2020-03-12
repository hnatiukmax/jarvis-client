package com.bugmakers.jarvistime.presentation.pages.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentMatrixLayoutBinding

class MatrixFragment : Fragment(){

    private lateinit var binding: FragmentMatrixLayoutBinding

    companion object {
        const val TAG = "MatrixFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matrix_layout, container, false)

        binding.text.text = "qqqqqqqqqqqq"
        return binding.getRoot()
    }
}