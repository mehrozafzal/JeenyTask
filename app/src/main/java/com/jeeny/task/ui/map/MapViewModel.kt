/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.ui.map

import com.jeeny.task.repository.repo.otp.MapRepository
import com.jeeny.task.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * A container for [MapActivity] related data to show on the UI.
 */
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : BaseViewModel<MapContract>() {


    fun testVM() {
        contract.let { it?.onTestButtonClicked() }
    }

}