package com.kotengine.chameleon.ui.glass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kotengine.chameleon.R
import kotlin.math.abs

private val AvatarPalette = listOf(
    Color(0xFF5B4FE8), Color(0xFF34C759), Color(0xFFFF9F0A),
    Color(0xFFFF375F), Color(0xFF64D2FF), Color(0xFFBF5AF2),
    Color(0xFFFFD60A), Color(0xFF30D158),
)

fun avatarColorFor(id: String): Color {
    val index = abs(id.hashCode()) % AvatarPalette.size
    return AvatarPalette[index]
}

@Composable
fun DeviceAvatar(
    id: String,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(avatarColorFor(id)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_android_robot),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(size * 0.6f),
        )
    }
}
