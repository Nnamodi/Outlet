package com.roland.android.remotedatasource.network.service

import com.roland.android.remotedatasource.network.model.ListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {

	@GET("/products")
	suspend fun fetchItems(
		@Query("organization_id") orgId: String,
		@Query("Appid") appId: String,
		@Query("Apikey") apiKey: String,
		@Query("size") size: Int = 15
	): ListModel

}