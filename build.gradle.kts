
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "pl.krystiankaniowski.composecharts"
version = "1.0"

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        ignoreFailures = false
        buildUponDefaultConfig = true
        parallel = true
        autoCorrect = false
        config.setFrom(files(project.rootDir.resolve("config/detekt.yml")))
        source = files(
            "src/main/java",
            "src/main/kotlin",
            "src/test/java",
            "src/test/kotlin",
            "src/commonMain/kotlin",
            "src/commonTest/kotlin",
            "src/androidMain/kotlin",
            "src/androidTest/kotlin",
            "src/desktopMain/kotlin",
            "src/desktopTest/kotlin",
            "src/jsMain/kotlin",
            "src/jsTest/kotlin",
            "build.gradle.kts",
            "settings.gradle.kts",
        )

        dependencies {
            detektPlugins(rootProject.libs.detekt.plugins.formatting)
        }
    }
}

// ----- DEPENDENCY UPDATES

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}