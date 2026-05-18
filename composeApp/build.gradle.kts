import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotzilla)

}
kotzilla {
    versionName = "1.0.0" // Your app version
}
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.decompose.android)
            implementation(libs.koin.android)
            implementation(libs.koin.android.lib)
        }
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":di"))
            implementation(project(":domain"))
            implementation(project(":sharedUI"))
            implementation(project(":MoodAddFT"))
            implementation(project(":SChallengeFT"))
            implementation(project(":SolutionFT"))
            implementation(project(":TargetFT"))
            implementation(project(":DecissionMakingFT"))
            implementation(project(":PrefrencesFT"))

            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.noarg)

            implementation(libs.koin.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.coil.compose)


            implementation(libs.haze)
            implementation(libs.haze.blur)
            /*Modifier.hazeEffect(state = hazeState) {
                blurEffect {
                    blurRadius = 20.dp
                    colorEffects = listOf(HazeColorEffect.tint(Color.Black.copy(alpha = 0.5f)))
                }
            }*/
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.vampyreworld.w2t"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.vampyreworld.w2t"
        minSdk = libs.versions.android.minSdk.get().toInt()
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

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.vampyreworld.w2t.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.vampyreworld.w2t"
            packageVersion = "1.0.0"
        }
    }
}
