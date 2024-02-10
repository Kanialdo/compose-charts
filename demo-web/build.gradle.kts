import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    jvmToolchain(17)

    js {
        moduleName = "app"
        binaries.executable()
        browser {
            useCommonJs()
            commonWebpackConfig {
                outputFileName = "$moduleName.js"
            }
        }
    }

    @Suppress("UnusedPrivateProperty")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.demoShared)
                implementation(projects.lib)
            }
        }
    }
}

compose.experimental {
    web.application {}
}
