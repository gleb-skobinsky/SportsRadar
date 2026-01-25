package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

class TabHistory {
    private val stack = ArrayDeque<BottomBarTab>()

    fun pushIfNotLast(tab: BottomBarTab) {
        if (stack.lastOrNull() != tab) stack.addLast(tab)
    }

    fun popPrevious(): BottomBarTab? {
        if (stack.size <= 1) return null
        stack.removeLast()
        return stack.lastOrNull()
    }

    private fun toList(): List<BottomBarTab> = stack.toList()

    companion object {
        val Saver: Saver<TabHistory, List<String>> = Saver(
            save = { history ->
                history.toList().map { it.name }
            },
            restore = { saved ->
                TabHistory().apply {
                    saved
                        .map { BottomBarTab.valueOf(it) }
                        .forEach { pushIfNotLast(it) }
                }
            }
        )
    }
}

@Composable
fun rememberTabHistory(): TabHistory {
    return rememberSaveable(saver = TabHistory.Saver) {
        TabHistory()
    }
}