package com.adziashko.todolist.gpt4.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

val animationFrames = listOf("|", "/", "-", "\\")

@Composable
fun LoadingScreen() {

    val infiniteTransition = rememberInfiniteTransition(label = "indefinite loading")
    val loadingState by infiniteTransition.animateFloat(
        label = "indefinite loading",
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            tween(16000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Box(Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = animationFrames[loadingState.toInt() % animationFrames.size],
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.h2
            )
        }
    }
}