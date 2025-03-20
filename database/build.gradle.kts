plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.yasirnaseem.androidtask.database"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    // Kotlin Core
    implementation(libs.androidx.core.ktx)
    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Coroutines
    implementation(libs.bundles.kotlinx.coroutines)
    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(project(":network"))
}