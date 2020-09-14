/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeeny.task.ui.BasicActivity


/**
 * Synthetic sugaring to get instance of [ViewModel] for [AppCompatActivity].
 */
inline fun <reified T : ViewModel> BasicActivity.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory).get(T::class.java)
}

/**
 * Synthetic sugaring to get instance of [ViewModel] for [Fragment].
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProvider(this).get(T::class.java)
}
