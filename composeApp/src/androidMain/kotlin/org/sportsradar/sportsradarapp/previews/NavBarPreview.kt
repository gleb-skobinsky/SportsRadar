package org.sportsradar.sportsradarapp.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppNavBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
@Preview
fun SportsRadarAppNavBarPreview() {
    val haze = remember { HazeState() }
    Scaffold(
        bottomBar = {
            SportsRadarAppNavBar(haze, null)
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Red.copy(alpha = .3f),
                            Color.Blue.copy(alpha = .3f)
                        )
                    )
                )
                .haze(haze)
        ) {
            Text(
                text = "Test",
                modifier = Modifier
                    .padding(it)
                    .align(Alignment.Center)
            )
            Text(
                text = "Test 2",
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}