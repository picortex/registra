plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.koncurrent.later.coroutines)
                api(libs.krono.api)
                api(projects.bitframeActorUser)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.kommander.coroutines)
            }
        }
    }
}