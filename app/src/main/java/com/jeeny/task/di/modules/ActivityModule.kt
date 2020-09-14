/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.di.modules


import com.jeeny.task.ui.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



/**
 * All your Activities participating in DI would be listed here.
 */
@Module(includes = [FragmentModule::class]) // Including Fragment Module Available For Activities
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMapActivity(): MapActivity



}
