import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // KSP + Serialization Plugins
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "eu.anifantakis.mitropolitikomoviesadvanced"
    compileSdk = 35

    defaultConfig {
        applicationId = "eu.anifantakis.mitropolitikomoviesadvanced"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // add your API_KEY (if your app needs one) inside the "local.properties" file
    // example:
    // API_KEY=abcdefg12345
    val apiKey = gradleLocalProperties(project.rootDir, providers).getProperty("API_KEY")
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            configureDebugBuildType(apiKey)
            isMinifyEnabled = false
        }
        release {
            configureReleaseBuildType(apiKey)
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Additional Icons
    implementation(libs.androidx.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Ktor
    implementation(libs.bundles.ktor)

    // Timber for logging
    implementation(libs.timber)

    // Google Fonts
    // https://developer.android.com/develop/ui/compose/text/fonts#use-downloadable-fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // Secured Android Persistence
    implementation(libs.secure.persist)
    implementation(libs.secure.persist.compose)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

private fun com.android.build.api.dsl.BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY_MOVIES", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL_MOVIES", "\"https://api.themoviedb.org\"")
    buildConfigField("String", "BASE_URL_MOVIE_IMAGE", "\"https://image.tmdb.org\"")
}

private fun com.android.build.api.dsl.BuildType.configureReleaseBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY_MOVIES", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL_MOVIES", "\"https://api.themoviedb.org\"")
    buildConfigField("String", "BASE_URL_MOVIE_IMAGE", "\"https://image.tmdb.org\"")
}