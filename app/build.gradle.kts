@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")

import AppConfig.versionName
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    // Apply the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")

}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        buildConfigField("String", "DATABASE_NAME", AppConfig.dbName)
        buildConfigField("String", "PREFERENCES_NAME", AppConfig.preferencesName)
        setProperty("archivesBaseName", "Comic-v$versionName")
        setProperty("archivesBaseName", "Comic-v$versionName")
    }
    signingConfigs {
        create("test") {
            storeFile = file(AppConfig.testSignConfigFile)
            keyAlias = "comicTest"
            keyPassword = "TGpdkSx4dXa4MwRu"
            storePassword = "TGpdkSx4dXa4MwRu"
            enableV2Signing = true
            enableV1Signing = true
        }
        create("release") {
            storeFile = file(AppConfig.prodSignConfigFile)
            keyAlias = "comicProd"
            keyPassword = "jmdJTH6Qa9MYYcGR"
            storePassword = "jmdJTH6Qa9MYYcGR"
            enableV2Signing = true
            enableV1Signing = true
        }
    }
    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
            isDebuggable = true
            versionNameSuffix = "D"
            resValue("string", "app_name", "Comic-v$versionName-debug")
            (this as ExtensionAware).configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
            buildConfigField("String", "BASE_URL", BaseUrl.development)
            buildConfigField("String", "USER_NAME", Username.development)
            buildConfigField("String", "PASSWORD", Password.development)
        }
        create("envTest") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            versionNameSuffix = "T"
            resValue("string", "app_name", "Comic-v$versionName-test")
            (this as ExtensionAware).configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", BaseUrl.testEnv)
            buildConfigField("String", "USER_NAME", Username.testEnv)
            buildConfigField("String", "PASSWORD", Password.testEnv)
            signingConfig = signingConfigs.getByName("test")
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            versionNameSuffix = "R"
            resValue("string", "app_name", "Comic-v$versionName-prod")
            (this as ExtensionAware).configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", BaseUrl.production)
            buildConfigField("String", "USER_NAME", Username.production)
            buildConfigField("String", "PASSWORD", Password.production)
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=kotlin.RequiresOptIn",
        )
        jvmTarget = "11"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/AL2.0")
            excludes.add("/META-INF/LGPL2.1")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }

    useLibrary("android.test.base")
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {

    // std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // kotlin
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation(platform(KotlinX.coroutines.bom))
    implementation(KotlinX.coroutines.android)
    implementation(KotlinX.serialization.json)
    implementation(KotlinX.coroutines.core)
    implementation(AndroidX.work.runtimeKtx)
    implementation(Google.firebase.crashlyticsGradlePlugin)

    // androidx
    implementation(AndroidX.preference.ktx)
    implementation(AndroidX.navigation.uiKtx)
    implementation(AndroidX.navigation.compose)
    implementation(AndroidX.activity.compose)
    implementation(AndroidX.constraintLayout.compose)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.lifecycle.viewModelSavedState)
    implementation(AndroidX.lifecycle.liveDataKtx)
    implementation(AndroidX.lifecycle.process)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha03")
    implementation(AndroidX.lifecycle.viewModelCompose)
    implementation(AndroidX.window)
    implementation(AndroidX.dataStore)
    implementation(AndroidX.dataStore.preferences)
    implementation(AndroidX.compose.compiler)
    implementation(AndroidX.compose.foundation)
    implementation(AndroidX.compose.material)
    implementation(AndroidX.compose.material.icons.extended)
    implementation(AndroidX.compose.ui.tooling)

    // android ui
    implementation(Google.android.material)
    implementation(Google.android.material.composeThemeAdapter)
    implementation("com.google.accompanist:accompanist-navigation-animation:_")


    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation(JakeWharton.timber)

    // dependency injection
    implementation(Google.dagger.hilt.android)
    implementation(AndroidX.hilt.navigationCompose)

    // kapt
    kapt(Google.dagger.hilt.compiler)
    kapt(AndroidX.hilt.compiler)

    // firebase
    implementation(platform(Firebase.bom))

    implementation(Firebase.analyticsKtx)
    implementation(Firebase.crashlyticsKtx)

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    //file directory util
    implementation("commons-io:commons-io:2.11.0")

    //KSP
    implementation("io.github.raamcosta.compose-destinations:core:1.6.33-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.6.33-beta")


    // test libs
    testImplementation(Testing.junit4)
    testImplementation(KotlinX.coroutines.test)
    testImplementation("com.google.truth:truth:1.1.2")
    androidTestImplementation(platform(KotlinX.coroutines.bom))
    androidTestImplementation(KotlinX.coroutines.test)
    androidTestImplementation(AndroidX.test.core)
    androidTestImplementation(AndroidX.test.monitor)
    androidTestImplementation(AndroidX.test.runner)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.compose.ui.testJunit4)
    androidTestImplementation(Google.dagger.hilt.android.testing)
    androidTestImplementation(Testing.kotest.assertions.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
