package org.sportsradar.sportsradarapp.common.utils

import kotlinx.browser.sessionStorage
import org.w3c.dom.get
import org.w3c.dom.set

class NavigationStorage {

    fun getNavIndex(): Int {
        return sessionStorage[NAV_INDEX_KEY].toIntOrZero()
    }

    fun setNavIndex(value: Int) {
        sessionStorage[NAV_INDEX_KEY] = value.toString()
    }

    fun getLastIndex(): Int {
        return sessionStorage[LAST_INDEX_KEY].toIntOrZero()
    }

    fun setLastIndex(value: Int) {
        sessionStorage[LAST_INDEX_KEY] = value.toString()
    }

    companion object {
        private const val NAV_INDEX_KEY = "navigation_index_key"
        private const val LAST_INDEX_KEY = "last_nav_index"

    }
}

internal fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0