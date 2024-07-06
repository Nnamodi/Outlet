package com.roland.android.remotedatasource.usecase

import android.util.Log
import com.roland.android.remotedatasource.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class UseCase<I: UseCase.Request, O: UseCase.Response> {

	fun execute(request: I) = process(request)
		.map {
			Result.Success(it) as Result<O>
		}
		.catch {
			Log.i("DataInfo", it.toString())
			emit(Result.Error(it))
		}

	internal abstract fun process(request: I): Flow<O>

	interface Request

	interface Response

}