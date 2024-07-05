package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json

data class Price(
	@Json(name = "EUR")
	val amount: List<Double> = emptyList()
)
