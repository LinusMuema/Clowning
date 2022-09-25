package com.moose.clowning.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ClowningTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        shapes = Shapes,
        content = content,
        typography = Typography,
        colors = ClowningColors,
    )
}