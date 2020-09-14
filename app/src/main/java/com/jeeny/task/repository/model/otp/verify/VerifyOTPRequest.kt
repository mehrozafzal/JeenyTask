package com.jeeny.task.repository.model.otp.verify

import com.google.gson.annotations.SerializedName

data class VerifyOTPRequest(

	@field:SerializedName("toNumber")
	var toNumber: String,

	@field:SerializedName("otp")
	var otp: String
)