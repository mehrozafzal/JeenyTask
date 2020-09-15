/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coordinate(

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)