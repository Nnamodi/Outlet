package com.roland.android.remotedatasource.repository.impl

import com.roland.android.remotedatasource.BuildConfig
import com.roland.android.remotedatasource.network.model.Item
import com.roland.android.remotedatasource.network.service.ProductsService
import com.roland.android.remotedatasource.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductsRepositoryImpl : ProductsRepository, KoinComponent {

	private val productsService : ProductsService by inject()

	override fun fetchItems(): Flow<List<Item>> = flow {
		emit(
			productsService.fetchItems(
				orgId = BuildConfig.ORGANIZATION_ID,
				appId = BuildConfig.APP_ID,
				apiKey = BuildConfig.API_KEY
			)
		)
	}.map { it.items }
		.catch { throw it }

}