plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "com.rvcoding.imhere"
version = "1.0.0"
application {
    mainClass.set("com.rvcoding.imhere.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.loggerSlf4j)
    implementation(libs.logback)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.serializationKotlinxJson)
    implementation(libs.ktor.server.callLogging)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.defaultHeaders)
    implementation(libs.ktor.server.htmlBuilder)

    implementation(project.dependencies.platform(libs.kotlincrypto.hash))
    implementation(libs.kotlincrypto.hash.sha2)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.tests)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}