package com.bugmakers.jarvistime.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.presentation.extensions.hideSoftKeyboard
import com.bugmakers.jarvistime.presentation.extensions.show
import com.bugmakers.jarvistime.presentation.view.dialog.InfoDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

internal abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    protected lateinit var binding: V
    protected abstract val viewModel: VM
    protected abstract val layoutId: Int

    private var baseActivity: BaseActivity<*, *>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.apply {
            lifecycleOwner = this@BaseFragment
            initView()
        }

        viewModel.apply {
            onShowMessage.observe {
                InfoDialog.newInstance(requireContext(), it).show(this@BaseFragment)
            }
            onCloseKeyboard.observe {
                baseActivity?.hideSoftKeyboard()
            }
            isProgressVisible.observe {
                baseActivity?.setProgressVisibility(it)
            }

            observeViewModel()
        }

        return binding.root
    }

    protected open fun V.initView() {}

    protected open fun VM.observeViewModel() {}

    override fun onDetach() {
        viewModel.onCleared()
        super.onDetach()
    }

    protected fun <P> LiveData<P>.observe(observerBody: (P) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            observerBody(it)
        })
    }
}