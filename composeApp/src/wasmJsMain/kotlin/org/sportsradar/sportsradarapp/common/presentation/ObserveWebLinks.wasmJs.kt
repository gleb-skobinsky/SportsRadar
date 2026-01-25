package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.savedstate.SavedState
import androidx.savedstate.read
import io.ktor.http.URLBuilder
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodedPath
import kotlinx.browser.window
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigatorImpl
import org.sportsradar.sportsradarapp.common.navigation.ScreensMeta
import org.sportsradar.sportsradarapp.common.navigation.handleBackOnTabRoot
import org.sportsradar.sportsradarapp.common.navigation.toNavUri
import org.w3c.dom.events.Event

private const val POPSTATE_EVENT_NAME = "popstate"

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
actual fun KMPNavigator.handleWebDeepLinkOnStart() {
    val navController = (this as? KMPNavigatorImpl)?.navController ?: return
    LaunchedEffect(Unit) {
        navController.awaitGraphIsReady()
        val deeplink = window.location.href
        val path = window.location.pathname

        if (path.isNotBlank() && path != "/") {
            navController.handleDeepLink(
                NavDeepLinkRequest(
                    uri = deeplink.toNavUri(),
                    action = null,
                    mimeType = null
                )
            )
        }

        navController.currentBackStackEntryFlow.collect { entry ->
            val currentUrl = window.location.pathname
            val meta = ScreensMeta.getByEntry(entry)
            val deeplinkPath = meta?.deeplink?.let(::URLBuilder)?.encodedPath
            if (deeplinkPath == currentUrl) {
                return@collect
            }

            when (meta?.deeplink) {
                currentUrl -> Unit
                null -> {
                    window.history.pushState(
                        data = null,
                        title = "",
                        url = "/"
                    )
                }
                else -> {
                    val hydratedLink = entry.hydratedDeepLink(meta.deeplink)
                    window.history.pushState(
                        data = null,
                        title = "",
                        url = hydratedLink
                    )
                }
            }
        }
    }

    val activityFinisher = rememberActivityFinisher()

    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            val isTabRoot = navController.currentBackStackEntry.isTabRoot()
            if (isTabRoot) {
                handleBackOnTabRoot(activityFinisher)
            } else {
                goBack()
            }
        }
        window.addEventListener(
            type = POPSTATE_EVENT_NAME,
            callback = listener
        )
        onDispose {
            window.removeEventListener(
                type = POPSTATE_EVENT_NAME,
                callback = listener
            )
        }
    }
}

private fun NavBackStackEntry?.isTabRoot(): Boolean {
    val entry = this ?: return false
    return ScreensMeta.getByEntry(entry)?.isTabRoot == true
}

private suspend fun NavController.awaitGraphIsReady() {
    currentBackStackEntryFlow
        .filterNotNull()
        .first()
}

fun NavBackStackEntry.hydratedDeepLink(
    deeplinkPath: String?,
): String? {
    val args = arguments ?: return null
    if (deeplinkPath == null) return null
    var uri: String = deeplinkPath
    val argsMap = args.map()
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