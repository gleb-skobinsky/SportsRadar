plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor.plugin)
    alias(libs.plugins.serialization)
}

group = "com.sportsradar"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.named<JavaExec>("run") {
    workingDir = rootProject.projectDir
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
    }
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://maven.pkg.jetbrains.space/public/p/ktor/eap/")
    maven(url = "https://mvnrepository.com/artifact/io.ktor/ktor-server-tests-jvm")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.simple.mail)
    implementation(libs.javax.activation)
    implementation(libs.jakarta.activation)
    implementation(projects.shared)
    implementation(libs.ktor.server)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.host)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
    implementation(libs.openapi.generator)
    implementation(libs.swagger.codegen)
    implementation(libs.kompendium)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.server.neg)
    implementation(libs.postgres)
    implementation(libs.bcrypt)
    implementation(libs.h2)
    implementation(libs.exposed.core)
    implementation(libs.exposed.datetime)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.migrations.core)
    implementation(libs.exposed.migrations.jdbc)
    implementation(libs.flyway)
    implementation(libs.flyway.psql)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.client.core.jvm)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback)
    implementation(libs.ktor.server.yaml.config)
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    testImplementation(libs.ktor.server.tests)
    // testImplementation(libs.kotlin.junit)
}

tasks.register<OpenApiGenerationTask>("generateOpenAPIYaml")

tasks.register<JavaExec>("generateMigrationScript") {
    group = "application"
    description = "Generate a migration script"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "com.sportsradar.db.GenerateMigrationScriptKt"
    workingDir = rootProject.projectDir
}

tasks.register<JavaExec>("reviewDatabaseMigrations") {
    group = "application"
    description = "Review database migrations"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "com.sportsradar.db.ReviewDatabaseMigrationsKt"
    workingDir = rootProject.projectDir
}
