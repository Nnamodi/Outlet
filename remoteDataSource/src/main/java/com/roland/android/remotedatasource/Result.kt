package com.roland.android.remotedatasource

sealed class Result<out T: Any> {

	data class Success<out T: Any>(val data: T) : Result<T>()

	data class Error(val throwable: Throwable) : Result<Nothing>()

}
