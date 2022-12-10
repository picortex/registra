package registra

import koncurrent.Later
import registra.params.SignUpParams
import registra.params.VerificationParams

class SignUpApiKtor(val config: SignUpApiKtorConfig<RegistraEndpoint>) : SignUpApi {
    private fun todo() = Later.reject(NotImplementedError("Not yet implemented"))

    override fun signUp(params: SignUpParams): Later<SignUpParams> = todo()

    override fun verify(params: VerificationParams): Later<VerificationParams> = todo()

    override fun sendVerificationLink(email: String): Later<String> = todo()
}