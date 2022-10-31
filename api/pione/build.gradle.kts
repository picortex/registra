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
                api(projects.registraApiCore)
                api(projects.pioneRest)
                api(projects.bitframeServiceBuilderApiKtor)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.expectCoroutines)
                implementation(projects.cacheMock)
                implementation(projects.authenticatorApiPione)
                implementation(projects.bitframeServiceBuilderApiMock)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(ktor.client.cio)
            }
        }
    }
}