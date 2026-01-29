package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import org.sportsradar.uiKit.icons.Favorites
import org.sportsradar.uiKit.icons.Home
import org.sportsradar.uiKit.icons.Profile

@Serializable
enum class BottomBarTab(
    val screen: Screens,
    val icon: ImageVector
) {
    HomeTab(
        screen = Screens.HomeScreen,
        icon = Home
    ),
    FavoritesTab(
        screen = Screens.FavoritesScreen,
        icon = Favorites
    ),
    ProfileTab(
        screen = Screens.ProfileScreen,
        icon = Profile
    )
}
