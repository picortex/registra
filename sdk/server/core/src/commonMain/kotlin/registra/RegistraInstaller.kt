package registra

import bitframe.ApplicationConfigBuilder

<<<<<<< HEAD
import bitframe.annotations.BitframeServerDsl
import bitframe.mapService

@BitframeServerDsl
fun <S> ApplicationConfigBuilder<S>.installRegistra(builder: (S) -> RegistraServer) {
    install {
        val server = builder(it)
        val config = server.config
        RegistraModule(config.mapService { signUp })
    }
}