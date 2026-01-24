package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.Serializable
import org.sportsradar.sportsradarapp.common.icons.Favorites
import org.sportsradar.sportsradarapp.common.icons.Home
import org.sportsradar.sportsradarapp.common.icons.Profile
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
sealed interface Screens {

    val tab: BottomBarTab?

    @Serializable
    object HomeTabScreen : Screens {
        override val tab = BottomBarTab.HomeTab
    }

    @Serializable
    object ProfileTabScreen : Screens {
        override val tab = BottomBarTab.ProfileTab
    }

    @Serializable
    object FavoritesTabScreen : Screens {
        override val tab = BottomBarTab.FavoritesTab
    }

    @Serializable
    object HomeScreen : Screens {
        override val tab = BottomBarTab.HomeTab
    }

    @Serializable
    object SignupScreen : Screens {
        override val tab = BottomBarTab.ProfileTab
    }

    @Serializable
    object LoginScreen : Screens {
        override val tab = BottomBarTab.ProfileTab
    }

    @Serializable
    data class ForgotPasswordScreen(val email: String? = null) : Screens {
        override val tab = BottomBarTab.ProfileTab
    }


    @Serializable
    object ProfileScreen : Screens {
        override val tab = BottomBarTab.ProfileTab
    }

    @Serializable
    object FavoritesScreen : Screens {
        override val tab = BottomBarTab.FavoritesTab
    }
}

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

fun <E : Enum<E>> enumNavType(entries: List<E>): NavType<E> {
    return object : NavType<E>(isNullableAllowed = false) {
        override fun put(bundle: SavedState, key: String, value: E) {
            bundle.write { putString(key, value.name) }
        }

        override fun get(bundle: SavedState, key: String): E? {
            val stringVal = bundle.read { getString(key) }
            return entries.find { it.name == stringVal }
        }

        override fun parseValue(value: String): E {
            return entries.first { it.name == value }
        }
    }
}
