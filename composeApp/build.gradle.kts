import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
}

kotlin {

    tasks.register("testClasses")
    androidTarget()
    val xcf = XCFramework()


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        target.binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).forEach { framework ->
            xcf.add(framework)
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            binaryOptions["iosDeploymentTarget"] = "17.0"
        }
    }


    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.compose.material.icons.extended)
        }
        commonMain.dependencies {
            implementation(libs.swipeable.kmp)
            implementation(libs.components.resources)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.composeIcons.tablerIcons)
            implementation(libs.compottie)
            implementation(libs.compottie.lite)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)

        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.sqldelight.sqlite.driver)
        }
    }
}


android {
    namespace = "org.michorga.everythinginone"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.michorga.everythinginone"
        minSdk = libs.versions.android.minSdk.get().toInt()
        //noinspection OldTargetApi
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "org.michorga.everythinginone.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.michorga.everythinginone"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.michorga.everythinginone.data.local.database")
        }
    }
}
