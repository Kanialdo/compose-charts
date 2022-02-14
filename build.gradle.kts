import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "pl.krystiankaniowski.composecharts"
version = "1.0"

plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}