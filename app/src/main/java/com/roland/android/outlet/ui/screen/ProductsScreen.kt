package com.roland.android.outlet.ui.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty
import com.roland.android.outlet.R
import com.roland.android.outlet.ui.theme.OutletTheme
import com.roland.android.remotedatasource.Constant.BASE_IMAGE_URL
import com.roland.android.remotedatasource.Constant.errorMessage
import com.roland.android.remotedatasource.network.model.Item
import com.roland.android.remotedatasource.usecase.GetProductsUseCase
import com.roland.android.remotedatasource.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
	data: Result<GetProductsUseCase.Response>?,
	retry: () -> Unit
) {
	val snackbarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()
	val context = LocalContext.current

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) }
			)
		},
		snackbarHost = { SnackbarHost(snackbarHostState) }
	) { paddingValues ->
		if (data is Result.Success) {
			val products = data.data.data
			Log.i("DataInfo", "$products")

			VerticalGrid(paddingValues) {
				items(products.size) { index ->
					Product(products[index])
				}
			}
		} else {
			LoadingScreen(
				isLoading = data == null,
				paddingValues = paddingValues
			)
		}
	}

	// shows error snackbar
	LaunchedEffect(data) {
		if (data !is Result.Error) return@LaunchedEffect
		scope.launch {
			val result = snackbarHostState.showSnackbar(
				message = data.throwable.errorMessage(),
				actionLabel = context.getString(R.string.retry)
			)
			if (result == ActionPerformed) retry()
		}
	}
}

@Composable
fun VerticalGrid(
	paddingValues: PaddingValues,
	modifier: Modifier = Modifier,
	content: LazyGridScope.() -> Unit
) {
	val layoutDirection = LocalLayoutDirection.current

	LazyVerticalGrid(
		columns = GridCells.Adaptive(150.dp),
		modifier = modifier.padding(start = 10.dp),
		contentPadding = PaddingValues(
			start = paddingValues.calculateStartPadding(layoutDirection),
			top = paddingValues.calculateTopPadding(),
			end = paddingValues.calculateEndPadding(layoutDirection),
			bottom = 100.dp
		)
	) {
		content()
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Product(
	item: Item,
	modifier: Modifier = Modifier
) {
	val state = remember { mutableStateOf<AsyncImagePainter.State>(Empty) }

	Column(
		modifier = modifier
			.padding(end = 10.dp, bottom = 10.dp)
			.clip(MaterialTheme.shapes.large)
			.background(Color.LightGray.copy(alpha = 0.2f))
	) {
		AsyncImage(
			model = BASE_IMAGE_URL + item.photos.last().url, // as it's impossible to delete images from the API, this fetches the last added image url
			contentDescription = item.name,
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 20.dp)
				.height(150.dp)
				.painterPlaceholder(state.value),
			onState = { state.value = it },
			contentScale = ContentScale.Crop
		)
		Text(
			text = item.name,
			modifier = Modifier
				.padding(horizontal = 10.dp)
				.basicMarquee(),
			fontSize = 18.sp,
			fontWeight = FontWeight.Medium,
			softWrap = false
		)
		Text(
			text = "€${item.price.find { it.currency == "EUR" }?.values?.last()}",
			modifier = Modifier
				.padding(start = 10.dp, bottom = 20.dp)
				.alpha(0.7f)
		)
	}
}

@Preview
@Composable
private fun ProductsScreenPreview() {
	OutletTheme {
		ProductsScreen(Result.Error(Exception("No internet connection"))) {}
	}
}