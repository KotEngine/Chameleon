package com.kotengine.chameleon.ui.glass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable (backdrop: Backdrop) -> Unit,
) {
    val backdrop = rememberLayerBackdrop()

    Box(modifier.fillMaxSize()) {
        Box(
            Modifier
                .layerBackdrop(backdrop)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1C1B2E), Color(0xFF2D2A4A), Color(0xFF191826)),
                    ),
                ),
        )
        content(backdrop)
    }
}
