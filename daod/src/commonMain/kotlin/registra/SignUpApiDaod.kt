package registra

import bitframe.ServiceConfigDaod
import koncurrent.Later
import mailer.EmailDraft
import registra.params.SignUpParams
import registra.params.VerificationLinkParams
import registra.params.VerificationParams

open class SignUpApiDaod(config: ServiceConfigDaod) : SignUpApi {
    private val mailer = config.mailer

    override fun signUp(params: SignUpParams): Later<SignUpParams> {
        return Later.resolve(params)
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> {
        return Later.resolve(params)
    }

    override fun sendVerificationLink(params: VerificationLinkParams): Later<VerificationLinkParams> {
        val draft = EmailDraft(
            subject = "Account Verification",
            body = "Follow the following link to verify your account\n${params.url}/token"
        )
        return mailer.send(draft, from = "service@test.com", to = params.email).andThen {
            Later.resolve(params)
        }
    }
}