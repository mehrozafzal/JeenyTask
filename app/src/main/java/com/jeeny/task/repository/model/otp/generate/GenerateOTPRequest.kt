package com.jeeny.task.repository.model.otp.generate

import com.google.gson.annotations.SerializedName

data class GenerateOTPRequest(

	@field:SerializedName("toNumber")
	var toNumber: String
)