package com.fazq.rimayalert.features.auth.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fazq.rimayalert.R
import com.fazq.rimayalert.core.ui.theme.AppColors

@Composable
fun MascotPlaceholderComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(AppColors.backgroundLight)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.notification_bell)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            restartOnPlay = false,
            speed = 1.1f
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(0.85f)
        )
    }
}