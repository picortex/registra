package registra

import pione.PiOneEndpoint
import registra.signup.SignUpApiConfigKtor
import registra.signup.SignupApiPiOneKtor

class RegistraApiPiOne(private val config: SignUpApiConfigKtor<PiOneEndpoint>) : RegistraApi {
    override val signUp by lazy { SignupApiPiOneKtor(config) }
}