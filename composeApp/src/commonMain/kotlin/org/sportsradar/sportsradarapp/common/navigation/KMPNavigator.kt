package org.sportsradar.sportsradarapp.common.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface KMPNavigator {
    val currentEntryFlow: Flow<NavBackStackEntry?>

    val currentEntry: NavBackStackEntry?

    fun pushIfNotLast(tab: BottomBarTab)

    fun popPrevious(): BottomBarTab?

    /**
     * Safely navigates back on back button click.
     */
    fun goBack()

    /**
     * Safely navigates to the given screen.
     */
    fun goTo(screen: Screens)

    fun replace(screen: Screens)

    fun replaceAll(screen: Screens)

    fun popUntil(screen: Screens)

    fun goToTab(tab: BottomBarTab)

    fun handleDeeplink(deeplink: String)

    companion object {
        val PreviewNavigator = object : KMPNavigator {
            override val currentEntryFlow: Flow<NavBackStackEntry?> = emptyFlow()
            override val currentEntry: NavBackStackEntry? = null
            override fun pushIfNotLast(tab: BottomBarTab) = Unit
            override fun popPrevious(): BottomBarTab? = null
            override fun goBack() = Unit
            override fun goTo(screen: Screens) = Unit
            override fun replace(screen: Screens) = Unit
            override fun replaceAll(screen: Screens) = Unit
            override fun popUntil(screen: Screens) = Unit
            override fun goToTab(tab: BottomBarTab) = Unit
            override fun handleDeeplink(deeplink: String) = Unit
        }
    }
}
