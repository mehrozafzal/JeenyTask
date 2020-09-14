/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.di.modules


import dagger.Module
import dagger.android.ContributesAndroidInjector



/**
 * All your Activities participating in DI would be listed here.
 */
@Module(includes = [FragmentModule::class]) // Including Fragment Module Available For Activities
abstract class ActivityModule {

/*    *//**
     * Marking Activities to be available to contributes for Android Injector
     *//*
    @ContributesAndroidInjector
    abstract fun contributeSignupActivity(): SignupActivity

    @ContributesAndroidInjector
    abstract fun contributeOtpActivity(): OtpActivity*/



}
