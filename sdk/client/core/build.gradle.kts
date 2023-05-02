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
                api(projects.registraApiCore)
                // api(projects.symphonyInputsIdentifier)
                api(projects.cinematicLiveKollections)
                // api(projects.bitframeServiceBuilderSdkClientCore)
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