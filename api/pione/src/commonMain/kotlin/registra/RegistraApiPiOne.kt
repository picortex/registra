package registra

import bitframe.ApiConfigKtor
import pione.PiOneEndpoint
import registra.signup.SignupApiPiOneKtor

class RegistraApiPiOne(private val config: ApiConfigKtor<PiOneEndpoint>) : RegistraApi {
    override val signUp by lazy { SignupApiPiOneKtor(config) }
}