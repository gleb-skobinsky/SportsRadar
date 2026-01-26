package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

interface TabHistory {
    fun pushIfNotLast(tab: BottomBarTab)
    fun popPrevious(): BottomBarTab?
}

internal class TabHistoryImpl : TabHistory {
    val stack = ArrayDeque<BottomBarTab>()

    override fun pushIfNotLast(tab: BottomBarTab) {
        if (stack.lastOrNull() != tab) stack.addLast(tab)
    }

    override fun popPrevious(): BottomBarTab? {
        if (stack.size <= 1) return null
        stack.removeLast()
        return stack.lastOrNull()
    }

    private fun toList(): List<BottomBarTab> = stack.toList()

    companion object {
        val Saver: Saver<TabHistoryImpl, List<String>> = Saver(
            save = { history ->
                history.toList().map { it.name }
            },
            restore = { saved ->
                TabHistoryImpl().apply {
                    saved
                        .map { BottomBarTab.valueOf(it) }
                        .forEach { pushIfNotLast(it) }
                }
            }
        )
    }
}

@Composable
expect fun rememberTabHistory(): TabHistory

@Composable
fun rememberTabHistoryCommon(): TabHistory {
    return rememberSaveable(saver = TabHistoryImpl.Saver) {
        TabHistoryImpl()
    }
}