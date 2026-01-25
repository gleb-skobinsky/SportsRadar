package org.sportsradar.sportsradarapp.common.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write

internal fun <E : Enum<E>> enumNavType(entries: List<E>): NavType<E> {
    return object : NavType<E>(isNullableAllowed = false) {
        override fun put(bundle: SavedState, key: String, value: E) {
            bundle.write { putString(key, value.name) }
        }

        override fun get(bundle: SavedState, key: String): E? {
            val stringVal = bundle.read { getString(key) }
            return entries.find { it.name == stringVal }
        }

        override fun parseValue(value: String): E {
            return entries.first { it.name == value }
        }
    }
}