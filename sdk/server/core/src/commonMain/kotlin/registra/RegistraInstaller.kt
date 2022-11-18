package registra

import bitframe.ApplicationConfigBuilder
import bitframe.annotations.BitframeServerDsl
import bitframe.mapService
import bitframe.ControllerConfig
import bitframe.ModuleConfig

@BitframeServerDsl
fun <S> ApplicationConfigBuilder<S>.installRegistra(builder: (S) -> RegistraServer) {
    install {
        val server = builder(it)
        val config = server.config
        RegistraModule(config.mapService { signUp })
    }
}