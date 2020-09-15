/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PoiListItem(

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("coordinate")
	var coordinate: Coordinate? = null,

	@field:SerializedName("fleetType")
	var fleetType: String? = null,

	@field:SerializedName("heading")
	var heading: Any? = null
)