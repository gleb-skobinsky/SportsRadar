package org.sportsradar.sportsradarapp.common.utils

private val LatinRegex = "[a-z]+".toRegex()

fun String.onlyLatin(): Boolean {
    val letters = filter { it.isLetter() }.lowercase()
    return LatinRegex.matches(letters)
}

fun String.satisfiesMinLength(min: Int) = length >= min