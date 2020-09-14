/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.di.modules

import android.content.Context
import android.content.res.Resources
import com.jeeny.task.BuildConfig
import com.jeeny.task.R
import com.jeeny.task.app.App
import com.jeeny.task.repository.api.ApiServices
import com.jeeny.task.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import dagger.Module
import dagger.Provides
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module(includes = [PrefrencesModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    /**
     * Static variables to hold base url's etc.
     */
    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
    }


    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }


    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): ViewPump {
        return ViewPump.builder()
            .addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("res/font/anonymous_pro.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                )
            )
            .build()
    }

}
