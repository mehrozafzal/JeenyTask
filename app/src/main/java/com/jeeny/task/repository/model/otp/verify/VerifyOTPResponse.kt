package com.jeeny.task.repository.model.otp.verify

import com.google.gson.annotations.SerializedName

data class VerifyOTPResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)