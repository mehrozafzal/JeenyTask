package com.jeeny.task.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {
    private var mNavigator: WeakReference<N>? = null
    val contract: N? get() = mNavigator.let { it?.get() }

    fun setContract(navigator: N) {
        mNavigator = WeakReference(navigator)
    }
}