package org.sportsradar.sportsradarapp.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sportsradar.uiKit.theme.primaryGradient
import org.sportsradar.uiKit.utils.angledGradient
import org.sportsradar.uiKit.utils.diagonalGradient
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer

@Composable
@Preview
fun PrimaryGradientPreview() {
    Box(
        Modifier
            .primaryGradient()
            .size(500.dp, 50.dp)
    )
}

@Composable
@Preview
fun DiagonalPreview() {
    Box(
        Modifier
            .diagonalGradient(
                listOf(Color.Red, Color.Blue),
            )
            .size(500.dp, 50.dp)
    )
}

@Composable
@Preview
fun AngledGradientPreview() {
    var angle by remember {
        mutableFloatStateOf(0f)
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(angle.toString(), color = Color.White)
            Slider(
                value = angle,
                valueRange = 0f..360f,
                onValueChange = {
                    angle = it
                }
            )
            24.dp.VerticalSpacer()
            Box(
                Modifier
                    .size(500.dp, 50.dp)
                    .angledGradient(
                        listOf(Color.Red, Color.Blue),
                        angle
                    )
            )
        }
    }
}