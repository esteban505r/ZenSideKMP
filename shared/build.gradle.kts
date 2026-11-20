import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)

            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.viewmodel.compose)
            api(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            api(libs.decompose)
            api(libs.decompose.compose)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.ktx)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.common)
            implementation(libs.sqldelight.sqlite.driver)
        }
    }
}

android {
    namespace = "com.zenside.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("ZenSideDatabase") {
            packageName.set("com.zenside.app.core.db")
        }
    }
}

compose.resources {
    packageOfResClass = "com.zenside.app.resources"
}
