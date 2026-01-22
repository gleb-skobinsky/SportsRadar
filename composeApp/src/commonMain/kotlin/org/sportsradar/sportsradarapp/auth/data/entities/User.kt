package org.sportsradar.sportsradarapp.auth.data.entities

import ApiUserBalance

data class User(
    val phone: String,
    val email: String,
    val balance: UserBalance
)

data class UserBalance(
    val amount: String,
    val currency: String
)

fun ApiUserBalance.toUserBalance(): UserBalance =
    UserBalance(
        amount = amount,
        currency = currency
    )