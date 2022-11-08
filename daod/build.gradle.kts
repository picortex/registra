plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
}

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.registraCore)
                api(projects.bitframeServiceBuilderDaod)
                api(projects.koncurrentPrimitivesCoroutines)
                api(projects.koncurrentLaterCoroutines)
            }
        }

        val commonTest by getting {
            dependencies {
                api(projects.expectCoroutines)
            }
        }
    }
}