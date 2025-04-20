import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //hilt
    alias(libs.plugins.googleDevToolsKsp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.fitfit.bannerit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fitfit.bannerit"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "0.1.1-beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("debug") {
            res.srcDirs("src/debug/res")
        }
    }

    signingConfigs {
        val keyStorePropertiesFile = rootProject.file("app/keystore/keystore.properties")
        val keyStoreProperties = Properties().apply {
            load(keyStorePropertiesFile.reader())
        }

        getByName("debug") {
            storeFile = file(keyStoreProperties["debugStoreFile"].toString())
            storePassword = keyStoreProperties["debugStorePassword"].toString()
            keyAlias = keyStoreProperties["debugKeyAlias"].toString()
            keyPassword = keyStoreProperties["debugKeyPassword"].toString()
        }
        create("release") {
            storeFile = file(keyStoreProperties["releaseStoreFile"].toString())
            storePassword = keyStoreProperties["releaseStorePassword"].toString()
            keyAlias = keyStoreProperties["releaseKeyAlias"].toString()
            keyPassword = keyStoreProperties["releaseKeyPassword"].toString()
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["appName"] = "BANner it! debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["appName"] = "@string/app_name"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}

dependencies {

    //modules
    implementation(project(":core:model"))
    implementation(project(":core:data:data"))
    implementation(project(":core:ui:designsystem"))
    implementation(project(":core:ui:ui"))

    implementation(project(":feature:image"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:report"))
    implementation(project(":feature:record"))
    implementation(project(":feature:more"))

    
    //compose bom
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    //core / lifecycle / activity
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //system ui controller
    implementation(libs.google.accompanist.systemuicontroller)

    //google maps
    implementation(libs.google.play.services.maps)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}