package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import org.sportsradar.sportsradarapp.common.icons.Favorites
import org.sportsradar.sportsradarapp.common.icons.Home
import org.sportsradar.sportsradarapp.common.icons.Profile
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KType
import kotlin.reflect.typeOf

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
    );

    companion object {
        val typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = mapOf(
            typeOf<BottomBarTab>() to enumNavType(BottomBarTab.entries)
        )
    }
}
