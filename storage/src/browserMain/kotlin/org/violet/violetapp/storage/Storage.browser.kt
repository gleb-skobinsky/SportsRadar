package org.violet.violetapp.storage

import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.sessionStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.get
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
 */
@OptIn(ExperimentalWasmJsInterop::class)
internal class JsStorage(
    private val useSession: Boolean,
) : Storage {

    private val webStorage: WebStorage by lazy {
        if (useSession) {
            sessionStorage
        } else {
            localStorage
        }
    }

    override fun subscribeToString(key: String): Flow<String?> =
        observeKey(key).distinctUntilChanged()

    override suspend fun getString(key: String): String? {
        return webStorage[key]
    }

    override suspend fun setString(key: String, value: String?) {
        webStorage.setValue(key, value)
    }

    override fun subscribeToInt(key: String): Flow<Int?> {
        return observeKey(key).map { it?.toIntOrNull() }.distinctUntilChanged()
    }

    override suspend fun getInt(key: String): Int? {
        return webStorage[key]?.toIntOrNull()
    }

    override suspend fun setInt(key: String, value: Int?) {
        webStorage.setValue(key, value?.toString())
    }

    override fun subscribeToFloat(key: String): Flow<Float?> {
        return observeKey(key).map { it?.toFloatOrNull() }.distinctUntilChanged()
    }

    override suspend fun getFloat(key: String): Float? {
        return webStorage[key]?.toFloatOrNull()
    }

    override suspend fun setFloat(key: String, value: Float?) {
        webStorage.setValue(key, value?.toString())
    }

    override fun subscribeToDouble(key: String): Flow<Double?> {
        return observeKey(key).map { it?.toDoubleOrNull() }.distinctUntilChanged()
    }

    override suspend fun getDouble(key: String): Double? {
        return webStorage[key]?.toDoubleOrNull()
    }

    override suspend fun setDouble(key: String, value: Double?) {
        webStorage.setValue(key, value?.toString())
    }

    override fun subscribeToLong(key: String): Flow<Long?> {
        return observeKey(key).map { it?.toLongOrNull() }.distinctUntilChanged()
    }

    override suspend fun getLong(key: String): Long? {
        return webStorage[key]?.toLongOrNull()
    }

    override suspend fun setLong(key: String, value: Long?) {
        webStorage.setValue(key, value?.toString())
    }

    override fun subscribeToBoolean(key: String): Flow<Boolean?> {
        return observeKey(key).map { it?.toBooleanStrictOrNull() }.distinctUntilChanged()
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return webStorage[key]?.toBooleanStrictOrNull()
    }

    override suspend fun setBoolean(key: String, value: Boolean?) {
        webStorage.setValue(key, value?.toString())
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
                val key = webStorage.key(index)
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
            webStorage.clear()
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

actual fun Scope.getStorage(
    useSession: Boolean,
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage = JsStorage(useSession)