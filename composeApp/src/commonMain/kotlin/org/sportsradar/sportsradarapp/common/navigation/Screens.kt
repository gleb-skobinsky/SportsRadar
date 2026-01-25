package org.sportsradar.sportsradarapp.common.navigation

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

enum class ScreensMeta(
    val tab: BottomBarTab?,
    val argsClass: KClass<out Screens>,
    val deeplink: String?,
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
        deeplink = "/home"
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
        deeplink = "/profile"
    ),
    Signup(
        tab = BottomBarTab.ProfileTab,
        argsClass = Screens.SignupScreen::class,
        deeplink = "/signup"
    ),
    Favorites(
        tab = BottomBarTab.FavoritesTab,
        argsClass = Screens.FavoritesScreen::class,
        deeplink = "/favorites"
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

        fun getByDisplayName(displayName: String): ScreensMeta? {
            println("Meta map: $screensMetaMap")
            return screensMetaMap[displayName]
        }

        fun getByScreen(screen: Screens): ScreensMeta? {
            return getByKClass(screen::class)
        }

        fun getByKClass(kClass: KClass<out Screens>): ScreensMeta? {
            return screensMetaMap[kClass.qualifiedName]
        }
    }
}

@Serializable
sealed interface Screens {

    @Transient
    val meta: ScreensMeta

    @Serializable
    object HomeTabScreen : Screens {
        @Transient
        override val meta = ScreensMeta.HomeTab
    }

    @Serializable
    object ProfileTabScreen : Screens {
        @Transient
        override val meta = ScreensMeta.ProfileTab
    }

    @Serializable
    object FavoritesTabScreen : Screens {
        @Transient
        override val meta = ScreensMeta.FavoritesTab
    }

    @Serializable
    object HomeScreen : Screens {
        @Transient
        override val meta = ScreensMeta.Home
    }

    @Serializable
    object SignupScreen : Screens {
        @Transient
        override val meta = ScreensMeta.Signup
    }

    @Serializable
    object AuthGraph : Screens {
        @Transient
        override val meta = ScreensMeta.Auth
    }

    @Serializable
    object LoginScreen : Screens {
        @Transient
        override val meta = ScreensMeta.Login
    }

    @Serializable
    data class ForgotPasswordScreen(val email: String? = null) : Screens {
        @Transient
        override val meta = ScreensMeta.ForgotPassword
    }


    @Serializable
    object ProfileScreen : Screens {
        @Transient
        override val meta = ScreensMeta.Profile
    }

    @Serializable
    object FavoritesScreen : Screens {
        @Transient
        override val meta = ScreensMeta.Favorites
    }
}
