package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.retain.RetainedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavUri
import androidx.navigation.navDeepLink

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RegisterTabVisitedAndBackHandler(
    tab: BottomBarTab?,
) {
    val navigator = LocalKmpNavigator.current
    RetainedEffect(navigator) {
        if (tab != null) {
            navigator.pushIfNotLast(tab)
        }
        onRetire {}
    }

    BackHandler {
        navigator.goBack()
    }
}

internal inline val NavBackStackEntry.screenRoute: String?
    get() = destination.route?.substringBefore('?')

internal fun NavBackStackEntry?.currentTab(): BottomBarTab? {
    val entry = this ?: return null
    val meta = ScreensMeta.getByEntry(entry) ?: return null
    return meta.tab
}

internal fun String.toNavDeeplink(): NavDeepLink = navDeepLink {
    uriPattern = this@toNavDeeplink
}

internal fun String.toNavUri(): NavUri = NavUri(this)

internal fun NavBackStackEntry?.isTabRoot(): Boolean {
    val entry = this ?: return false
    return ScreensMeta.getByEntry(entry)?.isTabRoot == true
}