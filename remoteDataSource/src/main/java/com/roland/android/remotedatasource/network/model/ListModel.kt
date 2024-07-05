package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json

data class ListModel(
	@Json(name = "items")
	val items: List<Item> = emptyList(),
	@Json(name = "size")
	val size: Int = 20
)
