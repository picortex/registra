package registra

import pione.PiOneEndpoint
import registra.signup.SignupApiPiOneKtor

class RegistraApiPiOne(private val config: SignUpApiRestKtorConfig<PiOneEndpoint>) : RegistraApi {
    override val signUp by lazy { SignupApiPiOneKtor(config) }
}