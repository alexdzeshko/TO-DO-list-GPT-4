package com.adziashko.todolist.gpt4.loading

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

val animationFrames = listOf("/", "|", "\\")

@Composable
fun LoadingScreen() {

    val infiniteTransition = rememberInfiniteTransition(label = "indefinite loading")
    val loadingState by infiniteTransition.animateFloat(
        label = "indefinite loading",
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            keyframes {
                durationMillis = 1600
                1f at 400
                2f at 1200
            },
            RepeatMode.Reverse
        )
    )
    val text = animationFrames[loadingState.toInt() % animationFrames.size]
    AnimatedContent(targetState = text, label = "loading indicator",
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = 100)) togetherWith
                    fadeOut(animationSpec = tween(durationMillis = 600))
        }) { targetText ->
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = targetText,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .width(60.dp)
                    .padding(end = 8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Loading...",
                style = MaterialTheme.typography.h3
            )
        }
    }
}