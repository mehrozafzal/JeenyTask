/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

// Referring this class as BaseActivity
typealias BasicActivity = DaggerActivity

/**
 * Activity providing Dagger support and [ViewModel] support
 */
abstract class DaggerActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun supportFragmentInjector() = dispatchingAndroidInjector


}
