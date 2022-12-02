pluginManagement {
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
    dependencyResolutionManagement {
        versionCatalogs {
            file("gradle/versions").listFiles().map {
                it.nameWithoutExtension to it.absolutePath
            }.forEach { (name, path) ->
                create(name) { from(files(path)) }
            }
        }
    }
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        mavenLocal()
    }

}

fun includeRoot(name: String, path: String) {
    include(":$name")
    project(":$name").projectDir = File(path)
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

rootProject.name = "registra"

includeSubs("identifier", "../identifier", "core")

includeSubs("functions", "../functions", "core")
includeSubs("expect", "../expect", "core", "coroutines")
includeSubs("kollections", "../kollections", "interoperable")
includeSubs("koncurrent-primitives", "../koncurrent/primitives", "core", "coroutines", "mock")
includeSubs("koncurrent-later", "../koncurrent/later", "core", "coroutines", "test")
includeSubs("cache", "../cache", "api", "browser", "file", "mock", "test")
includeSubs("response", "../response", "core")

includeSubs("formatter", "../formatter", "core")

includeSubs("live", "../live", "core", "kollections", "coroutines", "react", "test")

includeSubs("viewmodel", "../viewmodel", "core")
includeBuild("../kash/kash-generator")

includeSubs("kash", "../kash", "currency", "money")
includeSubs("krono", "../krono", "api", "kotlinx")

includeSubs(base = "mailer", "../mailer", "api", "mock", "smtp")
includeSubs(base = "events", path = "../events", "core", "inmemory", "react")

includeSubs("kronecker", "../kronecker", "core")

includeBuild("../geo/geo-generator")
includeSubs("geo", "../geo", "core", "countries")

includeSubs("presenters", "../presenters", "states", "actions")
includeSubs("presenters-collections", "../presenters/collections", "core")
includeSubs("presenters-collections-renderers", "../presenters/collections/renderers", "string", "console")
includeSubs("presenters-inputs", "../presenters/inputs", "core", "identifier")

includeSubs("bitframe-actor", path = "../bitframe/actors", "core", "user", "space", "app")
includeSubs("bitframe-api", path = "../bitframe/api", "core")
includeSubs("bitframe-dao", path = "../bitframe/daos", "core", "mock", "mongo", "file")
includeRoot("bitframe-dao", path = "../bitframe/daos/universal")
includeSubs("bitframe-service-builder", path = "../bitframe/service/builder", "core", "daod", "rest")
includeSubs("bitframe-service-builder-api", path = "../bitframe/service/builder/api", "core", "ktor", "mock")
includeSubs("bitframe-sdk-server", path = "../bitframe/sdk/server", "core", "ktor", "test")
includeSubs("bitframe-service-builder-sdk-client", path = "../bitframe/service/builder/sdk/client", "core", "react")
includeSubs("bitframe-service-builder-sdk-server", path = "../bitframe/service/builder/sdk/server", "core", "mock", "react")

includeSubs("pione", "../pione", "rest")
includeRoot("validation", "../bitframe/utils/validation")

includeSubs(base = "authenticator", path = "../authenticator", "core", "daod", "rest")
includeSubs(base = "authenticator-api", path = "../authenticator/api", "core", "ktor", "mock", "test", "pione")
includeRoot(name = "authenticator-api", path = "../authenticator/api/universal")
includeSubs(base = "authenticator-sdk-client", path = "../authenticator/sdk/client", "core")

includeSubs("registra", "../registra", "core")
includeSubs("registra-api", "../registra/api", "core", "pione")
includeSubs("registra-sdk", "../registra/sdk", "client")