// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.0-Beta1" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.20" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("io.ktor.plugin") version "2.3.11"
    kotlin("plugin.serialization") version "1.9.0" apply false
}