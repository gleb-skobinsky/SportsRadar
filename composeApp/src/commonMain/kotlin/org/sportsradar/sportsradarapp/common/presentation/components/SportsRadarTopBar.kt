package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.sportsradar.uiKit.icons.ArrowLeft
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator

data class IconData(
    val icon: ImageVector,
    val onClick: () -> Unit
) {
    companion object {
        @Composable
        fun default(): IconData {
            val navigator = LocalKmpNavigator.current
            return IconData(
                icon = ArrowLeft,
                onClick = navigator::goBack
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsRadarAppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    leftIcon: IconData? = IconData.default(),
    rightIcons: @Composable (RowScope.() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = title,
                style = LocalSportsRadarTheme.typography.bodyMedium,
                color = LocalSportsRadarTheme.colors.primaryContainer,
                modifier = Modifier.statusBarsPadding()
            )
        },
        navigationIcon = {
            leftIcon?.let {
                CommonIconButton(it.icon) {
                    it.onClick()
                }
            }
        },
        actions = {
            rightIcons?.invoke(this)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsRadarAppTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    leftIcon: IconData? = IconData.default(),
    rightIcons: @Composable (RowScope.() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            leftIcon?.let {
                CommonIconButton(it.icon) {
                    it.onClick()
                }
            }
        },
        actions = {
            rightIcons?.invoke(this)
        }
    )
}