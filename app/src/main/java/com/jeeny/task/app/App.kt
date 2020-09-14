/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.app

import android.app.Activity
import android.app.Application
import com.jeeny.task.di.base.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    @Inject
    lateinit var viewPump: ViewPump

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        ViewPump.init(viewPump)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}