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
                api(projects.koncurrentLaterCoroutines)
                api(projects.kronoApi)
                api(projects.bitframeActorUser)
            }
        }

        val commonTest by getting {
            dependencies {
                api(projects.expectCoroutines)
            }
        }
    }
}