package com.sportsradar.email.data

import com.sportsradar.email.data.EnvKeys.DB_NAME
import com.sportsradar.email.data.EnvKeys.DB_PASSWORD
import com.sportsradar.email.data.EnvKeys.DB_PORT
import com.sportsradar.email.data.EnvKeys.DB_USER
import com.sportsradar.email.data.EnvKeys.DEBUG_MODE
import com.sportsradar.email.data.EnvKeys.JWT_AUDIENCE
import com.sportsradar.email.data.EnvKeys.JWT_ISSUER
import com.sportsradar.email.data.EnvKeys.JWT_REALM
import com.sportsradar.email.data.EnvKeys.JWT_SECRET
import com.sportsradar.email.data.EnvKeys.SMTP_EMAIL_SENDER
import com.sportsradar.email.data.EnvKeys.SMTP_HOST
import com.sportsradar.email.data.EnvKeys.SMTP_PASSWORD
import com.sportsradar.email.data.EnvKeys.SMTP_PORT
import com.sportsradar.email.data.EnvKeys.SMTP_SUPPORTED
import com.sportsradar.email.data.EnvKeys.SMTP_USER_NAME
import java.io.File

private object EnvKeys {
    const val SMTP_SUPPORTED = "SMTP_SUPPORTED"
    const val SMTP_HOST = "SMTP_SERVER_HOST"
    const val SMTP_PORT = "SMTP_SERVER_PORT"
    const val SMTP_USER_NAME = "SMTP_SERVER_USER_NAME"
    const val SMTP_PASSWORD = "SMTP_SERVER_PASSWORD"
    const val SMTP_EMAIL_SENDER = "EMAIL_FROM"
    const val DB_PORT = "DATABASE_PORT"
    const val DB_NAME = "DATABASE_NAME"
    const val DB_USER = "DATABASE_USER"
    const val DB_PASSWORD = "DATABASE_PASSWORD"
    const val JWT_AUDIENCE = "JWT_AUDIENCE"
    const val JWT_ISSUER = "JWT_ISSUER"
    const val JWT_REALM = "JWT_REALM"
    const val JWT_SECRET = "JWT_SECRET"
    const val DEBUG_MODE = "DEBUG_MODE"
}

data class AppSecrets(
    val smtpServerHost: String,
    val smtpServerPort: Int,
    val smtpServerUserName: String,
    val smtpServerPassword: String,
    val emailFrom: String,
    val dbPort: Int,
    val dbName: String,
    val dbUser: String,
    val dbPassword: String,
    val jwtAudience: String,
    val jwtIssuer: String,
    val jwtRealm: String,
    val jwtSecret: String,
    val isDebug: Boolean,
    val smtpSupported: Boolean
) {
    companion object {
        fun fromEnvironment(): AppSecrets {
            val envFile = File(".env")
            val envMap = if (envFile.exists()) {
                envFile.readLines()
                    .associate { it.split('=').run { first() to last() } }
            } else emptyMap()
            return AppSecrets(
                smtpServerHost = envMap.getEnvString(SMTP_HOST),
                smtpServerPort = envMap.getEnvInt(SMTP_PORT),
                smtpServerUserName = envMap.getEnvString(SMTP_USER_NAME),
                smtpServerPassword = envMap.getEnvString(SMTP_PASSWORD),
                emailFrom = envMap.getEnvString(SMTP_EMAIL_SENDER),
                dbPort = envMap.getEnvInt(DB_PORT),
                dbName = envMap.getEnvString(DB_NAME),
                dbUser = envMap.getEnvString(DB_USER),
                dbPassword = envMap.getEnvString(DB_PASSWORD),
                jwtAudience = envMap.getEnvString(JWT_AUDIENCE),
                jwtIssuer = envMap.getEnvString(JWT_ISSUER),
                jwtRealm = envMap.getEnvString(JWT_REALM),
                jwtSecret = envMap.getEnvString(JWT_SECRET),
                isDebug = envMap.getEnvBool(DEBUG_MODE),
                smtpSupported = envMap.getEnvBool(SMTP_SUPPORTED)
            )
        }

        private fun Map<String, String>.getEnvString(key: String): String {
            return getValue(key)
        }

        private fun Map<String, String>.getEnvInt(key: String): Int = getEnvString(key).toInt()

        private fun Map<String, String>.getEnvBool(key: String): Boolean = getEnvString(key).toBoolean()
    }
}