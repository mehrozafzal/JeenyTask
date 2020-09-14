/*
 * Created by Muhammad Mehroz Afzal on 2020. 
 */

package com.jeeny.task.repository.repo.otp

import android.content.Context
import androidx.lifecycle.LiveData
import com.jeeny.task.app.AppExecutors
import com.jeeny.task.repository.api.ApiServices
import com.jeeny.task.repository.api.network.NetworkResource
import com.jeeny.task.repository.api.network.Resource
import com.jeeny.task.repository.model.otp.generate.GenerateOTPRequest
import com.jeeny.task.repository.model.otp.generate.GenerateOTPResponse
import com.jeeny.task.repository.model.otp.verify.VerifyOTPRequest
import com.jeeny.task.repository.model.otp.verify.VerifyOTPResponse

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 *
 */

@Singleton
class MapRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val context: Context,
    private val appExecutors: AppExecutors = AppExecutors()
) {

/*    fun getGenerateOtpResponseFromServer(generateOTPRequest: GenerateOTPRequest): LiveData<Resource<GenerateOTPResponse>> {
        return object : NetworkResource<GenerateOTPResponse>() {
            override fun createCall(): LiveData<Resource<GenerateOTPResponse>> {
                return apiServices.getOtpGenerateResponse(generateOTPRequest)
            }

        }.asLiveData()
    }*/

    fun getVerifyOtpResponseFromServer(VerifyOTPRequest: VerifyOTPRequest): LiveData<Resource<VerifyOTPResponse>> {
        return object : NetworkResource<VerifyOTPResponse>() {
            override fun createCall(): LiveData<Resource<VerifyOTPResponse>> {
                return apiServices.getVerifyOtpResponse(VerifyOTPRequest)
            }

        }.asLiveData()
    }
}