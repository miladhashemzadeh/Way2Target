plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.vampreworld.w2t.data"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "dataKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }
    jvm()
    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
                implementation(project(":domain"))
                implementation(libs.koin.core)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.noarg)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines.extensions)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.sqldelight.sqlite.driver)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.androidx.runner)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

sqldelight {
    databases {
        create("W2TDatabase") {
            packageName.set("com.vampyreworld.w2t.database")
            srcDirs("src/commonMain/sqldelight")
        }
    }
}
