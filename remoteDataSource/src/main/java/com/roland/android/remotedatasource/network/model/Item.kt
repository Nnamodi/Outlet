package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json

data class Item(
	@Json(name = "unique_id")
	val id: String = "",
	@Json(name = "name")
	val name: String = "",
	@Json(name = "photos")
	val photos: List<Image> = emptyList(),
	@Json(name = "current_price")
	val price: List<Price> = emptyList()
)
