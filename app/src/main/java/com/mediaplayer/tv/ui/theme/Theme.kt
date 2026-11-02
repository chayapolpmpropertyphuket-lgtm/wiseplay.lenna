package com.mediaplayer.tv.ui.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.MaterialTheme

@Composable
fun MediaPlayerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}