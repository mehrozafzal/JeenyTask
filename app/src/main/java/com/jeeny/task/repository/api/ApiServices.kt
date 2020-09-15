/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.api

import androidx.lifecycle.LiveData
import com.jeeny.task.repository.api.network.Resource
import com.jeeny.task.repository.model.VehicleResponse
import retrofit2.http.GET


/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    /**
     * Fetch news articles from Google news using GET API Call on given Url
     * Url would be something like this top-headlines?country=my&apiKey=XYZ
     */
    @GET("/raw/SwFd1znM")
    fun getVehicleResponse(): LiveData<Resource<VehicleResponse>>


}
