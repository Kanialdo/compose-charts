plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
}

android {
    compileSdk = 34

    namespace = "pl.krystiankaniowski.composecharts.demo"

    defaultConfig {
        applicationId = "pl.krystiankaniowski.composecharts.demo"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

dependencies {
    implementation(projects.demoShared)
    implementation(projects.lib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activityCompose)
}