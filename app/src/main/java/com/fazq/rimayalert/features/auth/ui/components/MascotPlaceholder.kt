package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.TextSizes

@Composable
fun MascotPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(Dimensions.boxSizeExtraLarge)
            .clip(CircleShape)
            .background(AppColors.backgroundLight),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🐾",
            fontSize = TextSizes.jumbo,
            color = AppColors.primary
        )
//        TODO: Replace with actual mascot image
        // Image(
        //     painter = painterResource(id = R.drawable.ic_mascot),
        //     contentDescription = "Mascota de RimayAlert",
        //     modifier = Modifier.fillMaxSize()
        // )
    }
}