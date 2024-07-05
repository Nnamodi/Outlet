package com.roland.android.remotedatasource.network.model

import com.squareup.moshi.Json

data class Image(
	@Json(name = "url")
	val url: String = ""
)
