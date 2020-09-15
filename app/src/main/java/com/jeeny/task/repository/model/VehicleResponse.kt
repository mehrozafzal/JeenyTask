/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.model

import com.google.gson.annotations.SerializedName

data class VehicleResponse(

	@field:SerializedName("poiList")
	val poiList: List<PoiListItem?>? = null
)