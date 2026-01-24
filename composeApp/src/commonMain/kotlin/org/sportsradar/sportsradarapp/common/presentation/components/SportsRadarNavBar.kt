package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import org.sportsradar.sportsradarapp.common.navigation.BottomBarTab
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.currentTab
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

private val NavBarHeight = 82.dp

@Composable
fun SportsRadarAppNavBarWrapper(
    hazeState: HazeState,
) {
    val navigator = LocalKmpNavigator.current
    val entry by navigator.currentEntryFlow.collectAsStateWithLifecycle(null)
    val tab by remember {
        derivedStateOf {
            entry.currentTab()
        }
    }

    AnimatedVisibility(
        visible = tab != null,
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        SportsRadarAppNavBar(
            navigator = navigator,
            hazeState = hazeState,
            currentTab = tab
        )
    }
}

@Composable
fun SportsRadarAppNavBar(
    hazeState: HazeState,
    navigator: KMPNavigator,
    currentTab: BottomBarTab?
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeDefaults.style(
                    backgroundColor = LocalSportsRadarTheme.colors.surfaceTint
                )
            )
            .fillMaxWidth()
            .height(NavBarHeight)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (tab in BottomBarTab.entries) {
            val isSelected = currentTab == tab
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable {
                        navigator.goToTab(tab)
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
}

