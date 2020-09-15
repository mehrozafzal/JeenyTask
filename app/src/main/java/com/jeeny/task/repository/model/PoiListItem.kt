/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PoiListItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("coordinate")
	val coordinate: Coordinate? = null,

	@field:SerializedName("fleetType")
	val fleetType: String? = null,

	@field:SerializedName("heading")
	val heading: Any? = null
)