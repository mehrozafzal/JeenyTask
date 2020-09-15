/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.jeeny.task.app.AppExecutors
import com.jeeny.task.repository.api.ApiServices
import com.jeeny.task.repository.api.network.NetworkResource
import com.jeeny.task.repository.api.network.Resource
import com.jeeny.task.repository.model.VehicleResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 *
 */

@Singleton
class MapRepository @Inject constructor(
    private val apiServices: ApiServices
) {

    fun getVehicleResponseFromServer(): LiveData<Resource<VehicleResponse>> {
        return object : NetworkResource<VehicleResponse>() {
            override fun createCall(): LiveData<Resource<VehicleResponse>> {
                return apiServices.getVehicleResponse()
            }
        }.asLiveData()
    }
}