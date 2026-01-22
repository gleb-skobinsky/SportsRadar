package org.sportsradar.sportsradarapp.common.utils.pager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

/**
 * An abstract generic pager class responsible for managing pagination logic.
 * This class should be extended to provide specific loading logic for different data types.
 *
 * @param T The type of items that this pager will manage.
 */
abstract class Pager<T> : KoinComponent {
    private val _currentPage = MutableStateFlow(1)
    private val _items = MutableStateFlow<List<T>>(emptyList())
    private val _state = MutableStateFlow<PagerState>(PagerState.Initial)

    /**
     * A StateFlow representing the current list of items.
     */
    val items: StateFlow<List<T>> = _items

    /**
     * A StateFlow representing the current state of the pager.
     */
    val state: StateFlow<PagerState> = _state

    /**
     * A StateFlow representing the current page number.
     */
    val currentPage: StateFlow<Int> = _currentPage

    /**
     * Abstract function to load a specific page. This function should be overridden
     * in subclasses to implement specific page loading logic, typically involving network
     * requests or database queries.
     *
     * @param page The page number to load.
     */
    abstract suspend fun loadPage(page: Int)

    /**
     * Loads the initial page.
     */
    suspend fun loadInitial() {
        loadPage(currentPage.value)
    }

    /**
     * Loads the next page if not already on the last page.
     */
    suspend fun loadNextPage() {
        if (_state.value is PagerState.LastPage) return
        loadPage(_currentPage.value)
    }

    /**
     * Appends new items to the current list.
     * Optionally applies a transformation to the combined list of old and new items.
     *
     * @param newItems The new items to append.
     * @param transformItems An optional transformation function to apply to the list.
     */
    protected fun appendNextItems(
        newItems: List<T>,
        transformItems: ((List<T>) -> List<T>)? = null,
    ) {
        val updatedList = if (transformItems != null) {
            transformItems(_items.value + newItems)
        } else {
            _items.value + newItems
        }

        _items.value = updatedList
        _currentPage.value = _currentPage.value.inc()
    }

    /**
     * Refreshes the pager, clearing current items and reloading from the first page.
     */
    suspend fun refresh() {
        _items.value = emptyList()
        _state.value = PagerState.Initial
        _currentPage.value = 1
        loadInitial()
    }

    /**
     * Sets the current state of the pager.
     *
     * @param state A lambda function returning the new state.
     */
    protected fun setPagerState(state: () -> PagerState) {
        _state.value = state.invoke()
    }
}
