package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import kotlinx.browser.window
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import org.sportsradar.sportsradarapp.common.navigation.ScreensMeta
import org.sportsradar.sportsradarapp.common.navigation.toNavDeeplink
import org.sportsradar.sportsradarapp.common.navigation.toNavUri
import org.w3c.dom.events.Event

private const val POPSTATE_EVENT_NAME = "popstate"

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
actual fun NavHostController.handleWebDeepLinkOnStart() {
    LaunchedEffect(Unit) {
        awaitGraphIsReady()
        val deeplink = window.location.href
        val path = window.location.pathname

        if (path.isNotBlank() && path != "/") {
            handleDeepLink(
                NavDeepLinkRequest(
                    uri = deeplink.toNavUri(),
                    action = null,
                    mimeType = null
                )
            )
        }

        currentBackStackEntryFlow.collect { entry ->
            val currentUrl = window.location.pathname
            val route = entry.destination.route?.substringBefore('?') ?: return@collect
            val meta = ScreensMeta.getByDisplayName(route)
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
                    window.history.pushState(
                        data = null,
                        title = "",
                        url = meta.deeplink
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            val newUrl = window.location.pathname
            if (!navigateUp()) {
                navigate(newUrl.toNavDeeplink())
            }
        }
        window.addEventListener(POPSTATE_EVENT_NAME, listener)
        onDispose { window.removeEventListener(POPSTATE_EVENT_NAME, listener) }
    }
}

private suspend fun NavHostController.awaitGraphIsReady() {
    currentBackStackEntryFlow
        .filterNotNull()
        .first()
}