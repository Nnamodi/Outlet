package com.roland.android.remotedatasource.network.service

import com.roland.android.remotedatasource.network.model.ListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {

	@GET("/products?{organization_id}&{size}&{Appid}&{Apikey}")
	suspend fun fetchItems(
		@Path("organization_id") orgId: String,
		@Query("Appid") appId: String,
		@Query("Apikey") apiKey: String,
		@Query("size") size: Int = 10
	): ListModel

}