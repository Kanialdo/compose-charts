plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    android()
    jvm("desktop")

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.lib)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
        }
    }
}

android {
    compileSdk = 34

    namespace = "pl.krystiankaniowski.composecharts.shared"

    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}
