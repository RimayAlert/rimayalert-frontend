package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AuthColors

@Composable
fun MascotPlaceholder(
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(AuthColors.Background),
        contentAlignment  = Alignment.Center
    ){
        Text(
            text = "🐾",
            fontSize = 48.sp,
            color = AuthColors.Primary
        )
//        TODO: Replace with actual mascot image
        // Image(
        //     painter = painterResource(id = R.drawable.ic_mascot),
        //     contentDescription = "Mascota de RimayAlert",
        //     modifier = Modifier.fillMaxSize()
        // )
    }
}

@Composable
fun AppLogo(
    modifier : Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SatsAlert",
            fontSize = 32.sp,
            fontWeight = FontWeight.Light,
            color = AuthColors.TextHint.copy(alpha = 0.3f),
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "powered by RimayAlert",
            fontSize = 12.sp,
            color = AuthColors.TextHint.copy(alpha = 0.4f)
        )
    }
}