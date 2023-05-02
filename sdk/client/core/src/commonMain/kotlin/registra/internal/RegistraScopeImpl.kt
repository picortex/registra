package registra.internal

import registra.RegistraApi
import registra.RegistraScope
import registra.RegistraScopeConfig
import registra.SignUpScene
import registra.VerificationScene

@PublishedApi
internal class RegistraScopeImpl(private val config: RegistraScopeConfig<RegistraApi>) : RegistraScope {
    private val registraConfig by lazy { config.map { it.signUp } }

    override val signUp by lazy { SignUpScene(registraConfig) }
    override val verification by lazy { VerificationScene(registraConfig) }
}