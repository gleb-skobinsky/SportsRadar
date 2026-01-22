package org.sportsradar.sportsradarapp.common.utils.pager

/**
 * Represents the various states of the Pager.
 */
sealed interface PagerState {
    data object Initial : PagerState
    data object Loading : PagerState
    data object Success : PagerState
    data object LastPage : PagerState
    data class Error(val message: String) : PagerState
}
