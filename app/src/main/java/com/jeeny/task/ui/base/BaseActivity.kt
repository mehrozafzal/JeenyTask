/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject


/**
 * Activity providing Dagger support and [ViewModel] support
 */
abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(),
    HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V


    abstract fun getBindingVariable(): Int
    abstract fun getViewModel(): V
    fun getViewDataBinding(): T = mViewDataBinding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

}
