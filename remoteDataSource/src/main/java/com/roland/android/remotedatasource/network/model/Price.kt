package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
	@Json(name = "currency")
	val currency: String = "",
	@Json(name = "values")
	val values: List<Double?> = emptyList()
)
