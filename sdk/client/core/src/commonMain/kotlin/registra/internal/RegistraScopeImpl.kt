package registra.internal

import registra.RegistraApi
import registra.RegistraScope
import registra.SignUpViewModel
import registra.VerificationViewModel
import viewmodel.ScopeConfig

@PublishedApi
internal class RegistraScopeImpl(private val config: ScopeConfig<RegistraApi>) : RegistraScope {
    private val registraConfig by lazy { config.map { it.signUp } }
    override val signUp by lazy { SignUpViewModel(registraConfig) }
    override val verification by lazy { VerificationViewModel(registraConfig) }
}