/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.di.components

import com.jeeny.task.app.App
import com.jeeny.task.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * AndroidInjectionModule::class to support Dagger
 * AppModule::class is loading all modules for app
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}
