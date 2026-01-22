package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import org.sportsradar.sportsradarapp.common.navigation.BottomBarTab
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.hasScreen
import org.sportsradar.uiKit.theme.PrimaryGradient
import org.sportsradar.sportsradarapp.common.presentation.modifiers.tintHazeChild
import dev.chrisbanes.haze.HazeState

private val NavBarHeight = 90.dp
private val HalfHeight = NavBarHeight / 2

@Composable
fun SportsRadarAppNavBarWrapper(
    hazeState: HazeState
) {
    val navigator = LocalKmpNavigator.current
    val entry by navigator.currentEntry.collectAsState(null)
    val navbarVisible by remember {
        derivedStateOf {
            BottomBarTab.entries.any {
                entry.hasScreen(it.screen) && it.hasNavBar
            }
        }
    }

    AnimatedVisibility(
        visible = navbarVisible,
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        SportsRadarAppNavBar(hazeState = hazeState, currentEntry = entry)
    }
}

@Composable
fun SportsRadarAppNavBar(
    hazeState: HazeState,
    currentEntry: NavBackStackEntry?
) {
    val navigator = LocalKmpNavigator.current
    with(LocalDensity.current) {
        BoxWithConstraints(
            Modifier
                .padding(10.dp)
                .height(NavBarHeight)
                .fillMaxWidth()
        ) {
            val path = remember(maxWidth) {
                val midWidth = maxWidth / 2
                Path().apply {
                    drawNavBarOutline(this, maxWidth, midWidth)
                }
            }
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .drawWithContent {
                        clipPath(path) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .tintHazeChild(
                        hazeState = hazeState,
                        color = LocalSportsRadarTheme.colors.surfaceTint
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (tab in BottomBarTab.entries) {
                    val isSelected = currentEntry.hasScreen(tab.screen)
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .clickable {
                                navigator.goTo(tab.screen)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = if (isSelected)
                                LocalSportsRadarTheme.colors.primary
                            else
                                LocalSportsRadarTheme.colors.primaryContainer
                        )
                    }
                }
            }
            CenterButtonGreenCircle()
        }
    }
}

@Composable
private fun BoxScope.CenterButtonGreenCircle() {
    Spacer(
        Modifier
            .align(Alignment.Center)
            .size(76.dp)
            .border(2.dp, PrimaryGradient, CircleShape)
    )
}

private fun Density.drawNavBarOutline(
    path: Path,
    maxWidth: Dp,
    midWidth: Dp,
) {
    val bottomY = NavBarHeight - 15.dp
    path.moveTo((midWidth - HalfHeight).toPx(), bottomY.toPx())
    path.lineTo(10.dp.toPx(), bottomY.toPx())
    path.cubicTo(
        x1 = 10.dp.toPx(),
        y1 = bottomY.toPx(),
        x2 = 0f,
        y2 = bottomY.toPx(),
        x3 = 0f,
        y3 = 65.dp.toPx()
    )
    path.lineTo(0.dp.toPx(), 25.dp.toPx())
    path.cubicTo(
        x1 = 0f,
        y1 = 25.dp.toPx(),
        x2 = 0f,
        y2 = 15.dp.toPx(),
        x3 = 10.dp.toPx(),
        y3 = 15.dp.toPx()
    )
    path.lineTo((midWidth - 50.dp).toPx(), 15.dp.toPx())
    path.cubicTo(
        x1 = (midWidth - 50.dp).toPx(),
        y1 = 15.dp.toPx(),
        x2 = (midWidth - 38.dp).toPx(),
        y2 = 15.dp.toPx(),
        x3 = (midWidth - 32.dp).toPx(),
        y3 = 10.dp.toPx()
    )
    path.cubicTo(
        x1 = (midWidth - 32.dp).toPx(),
        y1 = 10.dp.toPx(),
        x2 = midWidth.toPx(),
        y2 = (-13).dp.toPx(),
        x3 = (midWidth + 32.dp).toPx(),
        y3 = 10.dp.toPx()
    )
    path.lineTo((midWidth + 32.dp).toPx(), 10.dp.toPx())
    path.cubicTo(
        x1 = (midWidth + 32.dp).toPx(),
        y1 = 10.dp.toPx(),
        x2 = (midWidth + 38.dp).toPx(),
        y2 = 15.dp.toPx(),
        x3 = (midWidth + 50.dp).toPx(),
        y3 = 15.dp.toPx()
    )
    path.lineTo((maxWidth - 10.dp).toPx(), 15.dp.toPx())
    path.cubicTo(
        x1 = (maxWidth - 10.dp).toPx(),
        y1 = 15.dp.toPx(),
        x2 = maxWidth.toPx(),
        y2 = 15.dp.toPx(),
        x3 = maxWidth.toPx(),
        y3 = 25.dp.toPx()
    )
    path.lineTo(
        x = maxWidth.toPx(),
        y = 65.dp.toPx()
    )
    path.cubicTo(
        x1 = maxWidth.toPx(),
        y1 = 65.dp.toPx(),
        x2 = maxWidth.toPx(),
        y2 = 75.dp.toPx(),
        x3 = (maxWidth - 10.dp).toPx(),
        y3 = 75.dp.toPx()
    )
    path.lineTo((midWidth + 50.dp).toPx(), 75.dp.toPx())
    path.cubicTo(
        x1 = (midWidth + 50.dp).toPx(),
        y1 = 75.dp.toPx(),
        x2 = (midWidth + 38.dp).toPx(),
        y2 = 75.dp.toPx(),
        x3 = (midWidth + 32.dp).toPx(),
        y3 = 80.dp.toPx()
    )
    path.cubicTo(
        x1 = (midWidth + 32.dp).toPx(),
        y1 = 80.dp.toPx(),
        x2 = midWidth.toPx(),
        y2 = 103.dp.toPx(),
        x3 = (midWidth - 32.dp).toPx(),
        y3 = 80.dp.toPx()
    )
    path.cubicTo(
        x1 = (midWidth - 32.dp).toPx(),
        y1 = 80.dp.toPx(),
        x2 = (midWidth - 38.dp).toPx(),
        y2 = 75.dp.toPx(),
        x3 = (midWidth - 50.dp).toPx(),
        y3 = 75.dp.toPx()
    )
    path.close()
}