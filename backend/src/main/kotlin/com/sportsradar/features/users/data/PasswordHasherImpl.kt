package com.sportsradar.features.users.data

import at.favre.lib.crypto.bcrypt.BCrypt

class PasswordHasherImpl : PasswordHasher {
    override fun hash(password: String): String =
        BCrypt.withDefaults().hashToString(COST_FACTOR, password.toCharArray())

    override fun verify(password: String, hash: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hash).verified

    companion object {
        private const val COST_FACTOR = 12
    }
}