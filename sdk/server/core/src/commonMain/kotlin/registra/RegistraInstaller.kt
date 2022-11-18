package registra

import bitframe.ApplicationConfigBuilder
import bitframe.mapService

fun <S> ApplicationConfigBuilder<S>.installRegistra(builder: (S) -> RegistraServer) {
    install {
        val server = builder(it)
        val config = server.config
        RegistraModule(config.mapService { signUp })
    }
}