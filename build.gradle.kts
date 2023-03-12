// Top-level build file where you can add configuration options common to all sub-projects/modules.

import com.diffplug.gradle.spotless.SpotlessPlugin
import de.fayard.refreshVersions.core.versionFor
import org.jetbrains.gradle.ext.ProjectSettings
import org.jetbrains.gradle.ext.TaskTriggersConfig

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        // Kotlin Gradle plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        // Navigation SafeArgs Gradle Plugin
        classpath(AndroidX.navigation.safeArgsGradlePlugin)
        // Hilt Gradle plugin
        classpath(Google.dagger.hilt.android.gradlePlugin)
        // Google services Gradle plugin
        classpath(Google.playServicesGradlePlugin)
        // Add the Crashlytics Gradle plugin
        classpath(Firebase.crashlyticsGradlePlugin)
    }
}

plugins {
    id("com.android.application") apply false
    kotlin("android") apply false
    id("com.diffplug.spotless")
    id("org.jetbrains.gradle.plugin.idea-ext")
}
/*allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}*/

configurations {
    create("fake")
}
dependencies {
    // Adding this dependency just to get refreshVersions to track it
    add("fake", "com.pinterest:ktlint:_")
}

val ktlintUserData = mapOf(
    "android" to "true",
    "ij_kotlin_allow_trailing_comma_on_call_site" to true,
    "ij_kotlin_allow_trailing_comma" to true,
    "disabled_rules" to "indent,no-wildcard-imports,experimental:annotation",
)

val ktlintVersion = versionFor("version.com.pinterest..ktlint")
subprojects {
    apply<SpotlessPlugin>()
    spotless {
        kotlin {
            // Git branch to compare changes to
            // limit format enforcement to just the files changed by this feature branch
            ratchetFrom = "origin/compose"

            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint(ktlintVersion)
                .setUseExperimental(true)
                .editorConfigOverride(ktlintUserData)
            trimTrailingWhitespace()
            indentWithTabs()
            endWithNewline()
        }
        kotlinGradle {
            ktlint(ktlintVersion)
                .setUseExperimental(true)
                .editorConfigOverride(ktlintUserData)
        }
        format("xml") {
            target("**/*.xml")
            indentWithTabs()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

spotless {
    kotlinGradle {
        ktlint(ktlintVersion)
            .setUseExperimental(true)
            .editorConfigOverride(ktlintUserData)
        indentWithTabs()
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("misc") {
        target("**/*.md", "**/.gitignore")
        indentWithTabs()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

val spotlessHookTask = task<Copy>("addSpotlessApplyGitPreCommitHook") {
    from("scripts/git-hooks/pre-commit")
    into(".git/hooks")
}

idea {
    project {
        this as ExtensionAware
        configure<ProjectSettings> {
            this as ExtensionAware
            configure<TaskTriggersConfig> {
                afterSync(spotlessHookTask)
            }
        }
    }
}

tasks.getByName<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register("cleanTask", Delete::class) {
    delete(rootProject.buildDir)
}
