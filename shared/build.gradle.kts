import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
    }
    
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    iosX64 {
        binaries.framework {
            baseName = "imhere"
            binaryOptions["bundleId"] = "com.rvcoding.imhere.bid"
        }
    }
    iosArm64 {
        binaries.framework {
            baseName = "imhere"
            binaryOptions["bundleId"] = "com.rvcoding.imhere.bid"
        }
    }
    iosSimulatorArm64 {
        binaries.framework {
            baseName = "imhere"
            binaryOptions["bundleId"] = "com.rvcoding.imhere.bid"
        }
    }
    
    jvm()

    val osName = System.getProperty("os.name")
    val targetOs = when {
        osName == "Mac OS X" -> "macos"
        osName.startsWith("Win") -> "windows"
        osName.startsWith("Linux") -> "linux"
        else -> error("Unsupported OS: $osName")
    }
    val targetArch = when (val osArch = System.getProperty("os.arch")) {
        "x86_64", "amd64" -> "x64"
        "aarch64" -> "arm64"
        else -> error("Unsupported arch: $osArch")
    }
    val version = "0.8.12" // or any more recent version
    val target = "${targetOs}-${targetArch}"


    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.kotlin.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(project.dependencies.platform(libs.kotlincrypto.hash))
            implementation(libs.kotlincrypto.hash.sha2)
        }
        jvmMain.dependencies {
            implementation(libs.skiko.awt)
            implementation(libs.skiko.awt.runtime)
            implementation(compose.desktop.currentOs)
        }
        wasmJsMain.dependencies {
            implementation(npm("uuid", "10.0.0"))
        }
    }
}

android {
    namespace = "com.rvcoding.imhere.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
