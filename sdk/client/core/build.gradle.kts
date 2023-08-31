plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform sdk registration"

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.registraApiCore)
                api(libs.cinematic.live.kollections)
                api(libs.cinematic.scene.core)
                api(libs.captain.navigator.api)
                api(projects.hormoneCore)
                api(projects.symphonyInputIdentifier)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.cinematic.live.test)
                api(libs.kommander.coroutines)
            }
        }
    }
}