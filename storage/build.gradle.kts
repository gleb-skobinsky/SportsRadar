import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
}

kotlin {
    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    @Suppress("Unused")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
        val dataStoreMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.datastore)
            }
        }
        val iosMain by creating {
            dependsOn(dataStoreMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val jvmMain by getting {
            dependsOn(dataStoreMain)
        }
        androidMain {
            dependsOn(dataStoreMain)
        }
        val browserMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.kotlinx.browser)
            }
        }
        val jsMain by getting {
            dependsOn(browserMain)
        }
        val wasmJsMain by getting {
            dependsOn(browserMain)
        }
    }
}

android {
    namespace = "org.violet.violetapp.storage"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
