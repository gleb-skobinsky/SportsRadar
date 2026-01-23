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
            val dotenv = Dotenv(".env")
            dotenv.load()
            return AppSecrets(
                smtpServerHost = dotenv.getEnvString(SMTP_HOST),
                smtpServerPort = dotenv.getEnvInt(SMTP_PORT),
                smtpServerUserName = dotenv.getEnvString(SMTP_USER_NAME),
                smtpServerPassword = dotenv.getEnvString(SMTP_PASSWORD),
                emailFrom = dotenv.getEnvString(SMTP_EMAIL_SENDER),
                dbPort = dotenv.getEnvInt(DB_PORT),
                dbName = dotenv.getEnvString(DB_NAME),
                dbUser = dotenv.getEnvString(DB_USER),
                dbPassword = dotenv.getEnvString(DB_PASSWORD),
                jwtAudience = dotenv.getEnvString(JWT_AUDIENCE),
                jwtIssuer = dotenv.getEnvString(JWT_ISSUER),
                jwtRealm = dotenv.getEnvString(JWT_REALM),
                jwtSecret = dotenv.getEnvString(JWT_SECRET),
                isDebug = dotenv.getEnvBool(DEBUG_MODE),
                smtpSupported = dotenv.getEnvBool(SMTP_SUPPORTED)
            )
        }


    }
}

private class Dotenv(
    private val path: String,
) {
    private val dotEnvMap: MutableMap<String, String> = mutableMapOf()

    fun load() {
        val envFile = File(path)
        val envMap = if (envFile.exists()) {
            envFile.readLines()
                .associate { it.split('=')
                    .run { first() to last() } }
        } else emptyMap()
        dotEnvMap.putAll(envMap)
    }

    fun getEnvString(key: String): String {
        return dotEnvMap.getValue(key)
    }

    fun getEnvInt(key: String): Int = getEnvString(key).toInt()

    fun getEnvBool(key: String): Boolean = getEnvString(key).toBoolean()
}