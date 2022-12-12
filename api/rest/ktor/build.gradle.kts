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
                api(projects.registraApiRestCore)
                api(projects.bitframeServiceBuilderApiRestKtor)
                api(projects.responseCore)
            }
        }

        val commonTest by getting {
            dependencies {
                api(projects.registraApiTest)
            }
        }
    }
}