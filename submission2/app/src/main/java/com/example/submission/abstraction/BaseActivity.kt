package com.example.submission.abstraction

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : AppCompatActivity() {

    private lateinit var mViewDataBinding: B
    private lateinit var mViewModel: V

    val binding: B
        get() = mViewDataBinding
    val vm: V
        get() = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        mViewDataBinding.lifecycleOwner = this

        mViewModel = ViewModelProviders.of(this).get(getViewModelClass())
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int
    abstract fun getViewModelClass(): Class<V>
}