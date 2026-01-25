package org.sportsradar.sportsradarapp.common.navigation

import kotlinx.serialization.Serializable

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
    object AuthGraph : Screens {
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
