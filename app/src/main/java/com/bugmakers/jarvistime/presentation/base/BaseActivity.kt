package com.bugmakers.jarvistime.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.hideSoftKeyboard
import com.bugmakers.jarvistime.presentation.extensions.show
import com.bugmakers.jarvistime.presentation.view.dialog.InfoDialog
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

internal abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    KodeinAware {

    protected lateinit var binding: V
    protected abstract val viewModel: VM
    protected abstract val layoutId: Int

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        binding.apply {
            lifecycleOwner = this@BaseActivity
            initView()
        }

        viewModel.apply {
            onShowMessage.observe {
                InfoDialog.newInstance(this@BaseActivity, it).show(this@BaseActivity)
            }

            onCloseKeyboard.observe {
                hideSoftKeyboard()
            }

            observeViewModel()
        }
    }

    protected open fun V.initView() {}

    protected open fun VM.observeViewModel() {}

    protected fun <P> LiveData<P>.observe(observerBody: (P) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            observerBody(it)
        })
    }

    override fun onDestroy() {
        viewModel.onCleared()
        super.onDestroy()
    }

    fun setProgressVisibility(isVisible: Boolean) {
        viewModel.isProgressVisible.value = isVisible
    }
}