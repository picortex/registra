package registra

import koncurrent.Later
import koncurrent.FailedLater
import registra.params.SignUpParams
import registra.params.VerificationParams

class SignUpApiRestKtor(val config: SignUpApiRestKtorConfig<RegistraEndpoint>) : SignUpApi {
    private fun todo() = FailedLater(NotImplementedError("Not yet implemented"))

    override fun signUp(params: SignUpParams): Later<SignUpParams> = todo()

    override fun verify(params: VerificationParams): Later<VerificationParams> = todo()

    override fun sendVerificationLink(email: String): Later<String> = todo()
}