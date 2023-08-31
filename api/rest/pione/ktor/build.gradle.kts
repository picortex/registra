plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

val generated = buildDir.resolve("generated/commonTest/kotlin").apply {
    if (!exists()) mkdirs()
}

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.registraApiCore)
                api(projects.pioneRest)
            }
        }

        val commonTest by getting {
            kotlin.srcDirs(generated)
            dependencies {
                implementation(projects.koncurrentLaterCoroutines)
                implementation(libs.kommander.coroutines)
                implementation(projects.keepMock)
                implementation(projects.authenticatorApiRestPioneKtor)
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

afterEvaluate {
    val content = """
        package registra.pione
        
        val API_URL = "${System.getenv("API_URL") ?: ""}"
    """.trimIndent()
    println(content)
    generated.resolve("registra/pione/TestConfig.kt").apply {
        if (parentFile?.exists() == false) mkdir(parent)
        if (!exists()) createNewFile()
        writeText(content)
    }
}