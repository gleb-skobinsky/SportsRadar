package org.sportsradar.sportsradarapp.common.utils

private val SpecialSymbolsRegex = "[!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex()

fun String.upperCaseNumberOrSpecialSymbol(): Boolean {
    firstOrNull { it.isLetter() && it.isUpperCase() }?.let { return true }
    firstOrNull { it.isDigit() }?.let { return true }
    return SpecialSymbolsRegex.containsMatchIn(this)
}

fun Pair<String, String>.passwordsMatch() = first.isNotBlank() && second.isNotBlank() && first == second