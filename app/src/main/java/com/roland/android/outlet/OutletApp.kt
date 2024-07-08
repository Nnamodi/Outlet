package com.roland.android.outlet

import android.app.Application
import com.roland.android.outlet.ui.screen.ProductsViewModel
import com.roland.android.remotedatasource.di.NetworkModule.remoteDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class OutletApp : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@OutletApp)
			androidLogger(Level.INFO)
			modules(appModule, remoteDataModule)
		}
	}

}

private val appModule = module {
	viewModel { ProductsViewModel() }
}