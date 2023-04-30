plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

val tmp = 2

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.registraDaod)
                api(projects.registraRest)
                api(projects.cinematicLiveKollections)
                api(projects.bitframeServiceBuilderSdkServerCore)
                api(projects.bitframeSdkServerCore)
            }
        }

        val commonTest by getting {
            dependencies {
                api(projects.cinematicLiveTest)
                api(projects.kommanderCoroutines)
            }
        }
    }
}