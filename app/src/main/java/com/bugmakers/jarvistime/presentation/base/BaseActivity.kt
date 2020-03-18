package com.bugmakers.jarvistime.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.hideSoftKeyboard
import com.bugmakers.jarvistime.presentation.utils.showToastMessage
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

internal abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    KodeinAware {

    protected lateinit var binding: T
    protected abstract val viewModel: V
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
                showToastMessage(it.first, it.second)
            }
            onCloseKeyboard.observe{
                hideSoftKeyboard()
            }

            observeViewModel()
        }
    }

    protected open fun T.initView() {}

    protected open fun V.observeViewModel() {}

    override fun onDestroy() {
        viewModel.onCleared()
        super.onDestroy()
    }

    protected fun <P> LiveData<P>.observe(observerBody : (P) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            observerBody(it)
        })
    }
}