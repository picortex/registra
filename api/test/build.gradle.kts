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
                api(projects.mailerMock)
                api(libs.koncurrent.later.coroutines)
                api(libs.kommander.coroutines)
            }
        }
    }
}