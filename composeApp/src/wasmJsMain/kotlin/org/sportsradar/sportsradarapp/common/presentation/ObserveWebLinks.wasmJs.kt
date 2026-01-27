package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.savedstate.SavedState
import androidx.savedstate.read
import io.ktor.http.URLBuilder
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodedPath
import kotlinx.browser.window
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import org.sportsradar.sportsradarapp.common.navigation.BottomBarTab
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.ScreensMeta
import org.sportsradar.sportsradarapp.common.utils.NavigationStorage
import org.sportsradar.sportsradarapp.common.utils.toIntOrZero
import org.w3c.dom.PopStateEvent
import org.w3c.dom.events.Event

private const val POPSTATE_EVENT_NAME = "popstate"

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
actual fun KMPNavigator.handleWebDeepLinkOnStart() {
    val navStorage = remember { NavigationStorage() }

    LaunchedEffect(Unit) {
        awaitGraphIsReady()
        val deeplink = window.location.href
        val path = window.location.pathname

        if (path.isNotBlank() && path != "/") {
            handleDeeplink(deeplink)
        }

        observeLinkByEntry(
            navigator = this@handleWebDeepLinkOnStart,
            navStorage = navStorage
        )
    }

    HandleBrowserBackPress(
        navStorage = navStorage
    )
}

private suspend fun observeLinkByEntry(
    navigator: KMPNavigator,
    navStorage: NavigationStorage,
) {
    navigator.currentEntryFlow.collect { entry ->
        if (entry == null) return@collect
        val currentUrl = window.location.pathname
        val meta = ScreensMeta.getByEntry(entry)
        val deeplinkPath = meta?.deeplink?.let(::URLBuilder)?.encodedPath
        if (deeplinkPath == currentUrl) {
            return@collect
        }

        if (meta?.deeplink == null) {
            pushUrl(navStorage, "/")
        } else {
            pushUrl(navStorage, entry.hydratedDeepLink(meta.deeplink))
        }
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun pushUrl(navStorage: NavigationStorage, url: String) {
    val next = navStorage.getNavIndex() + 1
    navStorage.setNavIndex(next)
    window.history.pushState(
        data = next.toString().toJsString(),
        title = "",
        url = url
    )
    navStorage.setLastIndex(next)
}

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
private fun KMPNavigator.HandleBrowserBackPress(
    navStorage: NavigationStorage,
) {
    DisposableEffect(Unit) {
        val listener: (PopStateEvent) -> Unit = { e ->
            val currentIndex = (e.state as? JsString)?.toString().toIntOrZero()
            val lastIndex = navStorage.getLastIndex()

            when {
                currentIndex < lastIndex -> goBack()

                currentIndex > lastIndex -> {
                    val newPath = window.location.pathname
                    val newTab = getTabByLink(newPath)
                    if (newTab != null) {
                        goToTab(newTab)
                    } else {
                        handleDeeplink(window.location.href)
                    }
                }
            }

            navStorage.setLastIndex(currentIndex)
        }
        window.addEventListener(
            type = POPSTATE_EVENT_NAME,
            callback = listener as ((Event) -> Unit)
        )
        onDispose {
            window.removeEventListener(
                type = POPSTATE_EVENT_NAME,
                callback = listener
            )
        }
    }
}

private val tabsByLinks = BottomBarTab.entries.associateBy {
    ScreensMeta.getByScreen(it.screen)?.deeplink
}

private fun getTabByLink(path: String): BottomBarTab? {
    return tabsByLinks[path]
}

private suspend fun KMPNavigator.awaitGraphIsReady() {
    currentEntryFlow
        .filterNotNull()
        .first()
}

private fun NavBackStackEntry.hydratedDeepLink(
    deeplinkPath: String,
): String {
    val argsMap = arguments?.map().orEmpty()
    var uri: String = deeplinkPath

    argsMap.keys.forEach { key ->
        val value = argsMap[key]?.toString()
        uri = if (value == null) {
            uri.replace("$key={$key}", "")
        } else {
            uri.replace(
                oldValue = "{$key}",
                newValue = value.encodeURLParameter()
            )
        }
    }

    return uri.trimEnd('?').trimEnd('&')
}

private fun SavedState.map() = read { toMap() }


@Composable
actual fun rememberActivityFinisher() = ActivityFinisher.NoOp