package com.roland.android.remotedatasource

object Constant {

	internal const val TIMBU_BASE_URL = "https://api.timbu.cloud"
	const val BASE_IMAGE_URL = "https://api.timbu.cloud/images/"

	fun Throwable.errorMessage(): String = when (this) {
		is java.net.UnknownHostException -> "No internet connection"
		is javax.net.ssl.SSLException -> "Connection aborted"
		else -> message ?: "Oops... Something broke"
	}

}