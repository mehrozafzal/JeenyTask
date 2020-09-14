/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.model.otp.generate

import com.google.gson.annotations.SerializedName

data class GenerateOTPResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)