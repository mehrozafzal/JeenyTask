/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeeny.task.di.base.ViewModelFactory
import com.jeeny.task.di.base.ViewModelKey
import com.jeeny.task.ui.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    /**
     * Maps View Model
     */
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel): ViewModel

    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
