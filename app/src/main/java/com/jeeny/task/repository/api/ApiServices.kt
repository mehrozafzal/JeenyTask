/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.api

import androidx.lifecycle.LiveData
import com.jeeny.task.repository.api.network.Resource
import com.jeeny.task.repository.model.otp.verify.VerifyOTPRequest
import com.jeeny.task.repository.model.otp.verify.VerifyOTPResponse
import retrofit2.http.Body
import retrofit2.http.POST



/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    /**
     * Fetch news articles from Google news using GET API Call on given Url
     * Url would be something like this top-headlines?country=my&apiKey=XYZ
     */
    @POST("/afs-user-service/v1/bankId/twilio-otp/verify")
    fun getVerifyOtpResponse(@Body verifyRequest: VerifyOTPRequest): LiveData<Resource<VerifyOTPResponse>>

}
