import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.jetbrainsComposeHotReload)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
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
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    iosX64 {
        binaries.framework {
            baseName = "solotrek"
            binaryOptions["bundleId"] = "com.rvcoding.solotrek.bid"
        }
    }
    iosArm64 {
        binaries.framework {
            baseName = "solotrek"
            binaryOptions["bundleId"] = "com.rvcoding.solotrek.bid"
        }
    }
    iosSimulatorArm64 {
        binaries.framework {
            baseName = "solotrek"
            binaryOptions["bundleId"] = "com.rvcoding.solotrek.bid"
        }
    }

    jvmToolchain(17)
    jvm()

//    val osName = System.getProperty("os.name")
//    val targetOs = when {
//        osName == "Mac OS X" -> "macos"
//        osName.startsWith("Win") -> "windows"
//        osName.startsWith("Linux") -> "linux"
//        else -> error("Unsupported OS: $osName")
//    }
//    val targetArch = when (val osArch = System.getProperty("os.arch")) {
//        "x86_64", "amd64" -> "x64"
//        "aarch64" -> "arm64"
//        else -> error("Unsupported arch: $osArch")
//    }
//    val version = "0.8.12" // or any more recent version
//    val target = "${targetOs}-${targetArch}"


    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.androidx.core.ktx)
            implementation(libs.google.android.gms.play.services.location)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.android)
            implementation(libs.koin.androidTest)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.cio)

            implementation(libs.ksafe)
            implementation(libs.ksafe.compose)
            implementation(libs.kotlinx.coroutines.android)
        }
        iosMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.ktor.client.darwin)

            implementation(libs.ksafe)
            implementation(libs.ksafe.compose)
        }
        commonMain.dependencies {
            implementation(libs.kotlin.stdlib)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlin.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(project.dependencies.platform(libs.kotlincrypto.hash))
            implementation(libs.kotlincrypto.hash.sha2)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serializationKotlinxJson)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.skiko.awt)
            implementation(libs.skiko.awt.runtime)
            implementation(libs.ktor.client.core.jvm)
            implementation(libs.ktor.client.cio)

            implementation(compose.desktop.currentOs)
        }
        wasmJsMain.dependencies {
            implementation(libs.kotlin.stdlib.wasm.js)
            implementation(npm("uuid", "10.0.0"))
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "com.rvcoding.solotrek.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        val mapsApiKey = project.loadLocalProperty(
            path = "secrets.properties",
            propertyName = "MAPS_API_KEY",
        )
        buildConfigField("String", "mapsApiKey", "\"$mapsApiKey\"")
        resValue("string", "mapsApiKey", mapsApiKey)
    }

    buildFeatures {
        buildConfig = true
    }
}

fun Project.loadLocalProperty(
    path: String,
    propertyName: String,
): String {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file(path)
    try {
        localProperties.load(localPropertiesFile.inputStream())
        val property = localProperties.getProperty(propertyName)
        return property
    } catch (e: Exception) {
        throw GradleException("Error loading local property $propertyName: ${e.toString()}")
    }
}