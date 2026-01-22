package org.violet.violetapp.storage

import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.set
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.unsafeCast
import org.w3c.dom.Storage as WebStorage

/**
 * A browser-specific [Storage] implementation using localStorage.
 *
 * This implementation provides theme storage for web environments using the browser's
 * localStorage API. It includes reactive change notifications through custom DOM events,
 * enabling multiple tabs or components to stay synchronized.
 *
 * @param preferencesKey The localStorage key used to store the theme preference.
 */
@OptIn(ExperimentalWasmJsInterop::class)
internal class JsStorage : Storage {

    override fun subscribeByKey(key: String): Flow<String?> = observeKey(key).distinctUntilChanged()

    override suspend fun setByKey(key: String, value: String?) {
        localStorage.setValue(key, value)
    }

    /**
     * Sets a value in localStorage and dispatches a custom event for reactivity.
     *
     * This extension function updates localStorage and simultaneously dispatches
     * a custom DOM event to notify other parts of the application about the change.
     */
    private fun WebStorage.setValue(key: String, value: String?) {
        document.dispatchEvent(
            CustomEvent(
                type = STORAGE_EVENT_KEY,
                eventInitDict = CustomEventInit(
                    detail = EventDetail(key, value)
                )
            )
        )
        if (value == null) {
            removeItem(key)
        } else {
            set(key, value)
        }
    }

    override suspend fun clearAll() {
        try {
            for (index in 0 until localStorage.length) {
                val key = localStorage.key(index)
                if (key != null) {
                    document.dispatchEvent(
                        CustomEvent(
                            type = STORAGE_EVENT_KEY,
                            eventInitDict = CustomEventInit(
                                detail = EventDetail(key, null)
                            )
                        )
                    )
                }
            }
            localStorage.clear()
        } catch (_: Exception) {
            currentCoroutineContext().ensureActive()
        }
    }

    /**
     * Creates a reactive [Flow] that observes changes to a specific localStorage key.
     *
     * The flow emits new values whenever the specified key is modified through
     * the [setValue] extension function, enabling real-time synchronization
     * across components.
     */
    private fun observeKey(key: String): Flow<String?> {
        return callbackFlow {
            val listener = createEventListener { event ->
                val detail = (event as? CustomEvent)?.detail ?: return@createEventListener
                detail.unsafeCast<EventDetail>().let { detail ->
                    if (detail.key == key) {
                        launch {
                            send(detail.value)
                        }
                    }
                }
            }
            document.addEventListener(STORAGE_EVENT_KEY, listener)
            awaitClose {
                document.removeEventListener(STORAGE_EVENT_KEY, listener)
            }
        }
    }

    private companion object {
        const val STORAGE_EVENT_KEY = "localStorageUpdate"
    }
}

actual fun Scope.getThemeStorage(
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage = JsStorage()