plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    androidLibrary {
        namespace = "com.vampyreworld.w2t.profileft"
        compileSdk = 36
        minSdk = 24
    }

    val xcfName = "ProfileFTKit"

    iosX64 { binaries.framework { baseName = xcfName } }
    iosArm64 { binaries.framework { baseName = xcfName } }
    iosSimulatorArm64 { binaries.framework { baseName = xcfName } }
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(project(":sharedUI"))
                implementation(project(":di"))
                implementation(project(":domain"))
                implementation(project(":core"))
                implementation(libs.decompose)
                implementation(libs.decompose.extensions.compose)
                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.extensions.coroutines)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.haze)
                implementation(libs.haze.blur)
            }
        }
    }
}
