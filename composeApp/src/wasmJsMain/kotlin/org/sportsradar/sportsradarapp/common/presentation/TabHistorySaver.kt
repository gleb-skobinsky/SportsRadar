package org.sportsradar.sportsradarapp.common.presentation

import kotlinx.browser.sessionStorage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import org.sportsradar.sportsradarapp.common.navigation.BottomBarTab

class TabHistorySaver {

    fun saveTabs(tabs: List<BottomBarTab>) {
        val jsonArray = JsonArray(tabs.map { JsonPrimitive(it.name) })
        sessionStorage.setItem(TABS_KEY, jsonArray.toString())
    }

    fun restoreTabs(): List<BottomBarTab> {
        val raw = sessionStorage.getItem(TABS_KEY) ?: return emptyList()
        val jsonArray = Json.parseToJsonElement(raw).jsonArray.map {
            it.toString().trim('\"')
        }
        return jsonArray.map { BottomBarTab.valueOf(it) }
    }

    companion object {
        private const val TABS_KEY = "browser_tabs_key"
    }
}