package com.roland.android.remotedatasource.repository

import com.roland.android.remotedatasource.network.model.Item
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

	fun fetchItems(): Flow<List<Item>>

}