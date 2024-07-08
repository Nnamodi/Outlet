package com.roland.android.outlet.ui.screen

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.roland.android.outlet.ui.theme.OutletTheme

@Composable
fun LoadingScreen(
	isLoading: Boolean,
	paddingValues: PaddingValues,
	modifier: Modifier = Modifier
) {
	VerticalGrid(paddingValues, modifier) {
		items(10) {
			BoxItem(isLoading)
		}
	}
}

@Composable
private fun BoxItem(
	isLoading: Boolean,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.padding(end = 10.dp, bottom = 10.dp)
			.clip(MaterialTheme.shapes.large)
			.background(Color.LightGray.copy(alpha = 0.2f))
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 20.dp)
				.height(150.dp)
				.then(Modifier.shimmerModifier(isLoading))
		)
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 10.dp, end = 40.dp, bottom = 10.dp)
				.height(16.dp)
				.then(Modifier.shimmerModifier(isLoading))
		)
		Box(
			modifier = Modifier
				.padding(start = 10.dp, bottom = 20.dp)
				.size(30.dp, 14.dp)
				.alpha(0.7f)
				.then(Modifier.shimmerModifier(isLoading))
		)
	}
}

private fun Modifier.shimmerModifier(isLoading: Boolean): Modifier = composed {
	if (isLoading) {
		background(rememberAnimatedShimmerBrush())
	} else {
		background(Color.LightGray.copy(alpha = 0.6f))
	}
}

fun Modifier.painterPlaceholder(state: AsyncImagePainter.State): Modifier = composed {
	if (state is AsyncImagePainter.State.Loading) {
		background(rememberAnimatedShimmerBrush())
	} else {
		drawBehind {
			drawRect(Color.LightGray.copy(alpha = 0.6f))
		}
	}
}

@Composable
private fun rememberAnimatedShimmerBrush(): Brush {
	val shimmerColors = listOf(
		Color.LightGray.copy(alpha = 0.6f),
		Color.LightGray.copy(alpha = 0.2f),
		Color.LightGray.copy(alpha = 0.6f)
	)

	val transition = rememberInfiniteTransition(label = "shimmer transition")
	val translateAnim = transition.animateFloat(
		initialValue = 0f,
		targetValue = 1000f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1000,
				easing = FastOutLinearInEasing
			),
			repeatMode = RepeatMode.Reverse
		), label = "shimmer animation"
	)

	return Brush.linearGradient(
		colors = shimmerColors,
		start = Offset.Zero,
		end = Offset(x = translateAnim.value, y = translateAnim.value)
	)
}

@Preview
@Composable
private fun LoadingScreenPreview() {
	OutletTheme {
		LoadingScreen(isLoading = true, paddingValues = PaddingValues())
	}
}