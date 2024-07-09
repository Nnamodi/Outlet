package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
	@Json(name = "unique_id")
	val id: String = "",
	@Json(name = "name")
	val name: String = "",
	@Json(name = "description")
	val price: String = "", // a workaround concerning the bug I narrated in `Price` file
	@Json(name = "photos")
	val photos: List<Image> = emptyList(),
	@Json(name = "current_price")
	val currentPrice: List<Price> = emptyList()
)
