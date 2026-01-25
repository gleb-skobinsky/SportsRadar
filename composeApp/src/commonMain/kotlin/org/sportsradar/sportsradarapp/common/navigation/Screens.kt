package org.sportsradar.sportsradarapp.common.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

enum class ScreensMeta(
    val tab: BottomBarTab?,
    val argsClass: KClass<out Screens>,
    val deeplink: String?,
    val isTabRoot: Boolean = false,
) {
    HomeTab(
        tab = BottomBarTab.HomeTab,
        argsClass = Screens.HomeTabScreen::class,
        deeplink = null
    ),
    ProfileTab(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.ProfileTabScreen::class,
        deeplink = null
    ),
    FavoritesTab(
        tab = BottomBarTab.FavoritesTab,
        argsClass = Screens.FavoritesTabScreen::class,
        deeplink = null
    ),
    Home(
        tab = BottomBarTab.HomeTab,
        argsClass = Screens.HomeScreen::class,
        deeplink = "/home",
        isTabRoot = true,
    ),
    Auth(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.AuthGraph::class,
        deeplink = null
    ),
    Login(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.LoginScreen::class,
        deeplink = "/login"
    ),
    ForgotPassword(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.ForgotPasswordScreen::class,
        deeplink = "/forgot-password?email={email}"
    ),
    Profile(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.ProfileScreen::class,
        deeplink = "/profile",
        isTabRoot = true,
    ),
    Signup(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.SignupScreen::class,
        deeplink = "/signup"
    ),
    Favorites(
        tab = BottomBarTab.FavoritesTab,
        argsClass = Screens.FavoritesScreen::class,
        deeplink = "/favorites",
        isTabRoot = true,
    );

    fun navDeeplink(hostWithScheme: String): NavDeepLink? {
        return deeplink?.let { path ->
            navDeepLink {
                uriPattern = "$hostWithScheme$path"
            }
        }
    }

    companion object {
        private val screensMetaMap: Map<String?, ScreensMeta> = ScreensMeta.entries.associateBy {
            it.argsClass.qualifiedName
        }

        fun getByScreen(screen: Screens): ScreensMeta? {
            return getByKClass(screen::class)
        }

        fun getByKClass(kClass: KClass<out Screens>): ScreensMeta? {
            return screensMetaMap[kClass.qualifiedName]
        }

        fun getByEntry(entry: NavBackStackEntry): ScreensMeta? {
            return screensMetaMap[entry.screenRoute]
        }
    }
}

@Serializable
sealed interface Screens {

    fun hydratedDeeplink(): String? {
        val meta = ScreensMeta.getByScreen(this) ?: return null
        return meta.deeplink
    }

    @Serializable
    object HomeTabScreen : Screens {

    }

    @Serializable
    object ProfileTabScreen : Screens {

    }

    @Serializable
    object FavoritesTabScreen : Screens {

    }

    @Serializable
    object HomeScreen : Screens {

    }

    @Serializable
    object SignupScreen : Screens {

    }

    @Serializable
    object AuthGraph : Screens {

    }

    @Serializable
    object LoginScreen : Screens {

    }

    @Serializable
    data class ForgotPasswordScreen(val email: String? = null) : Screens {

    }


    @Serializable
    object ProfileScreen : Screens {

    }

    @Serializable
    object FavoritesScreen : Screens {

    }
}
