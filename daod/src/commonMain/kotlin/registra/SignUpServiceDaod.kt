package registra

import bitframe.dao.find
import bitframe.dao.get
import bitframe.dao.isEqualTo
import koncurrent.Later
import mailer.EmailDraft
import registra.params.SignUpParams
import registra.params.VerificationParams
import kotlin.random.Random

open class SignUpServiceDaod(private val config: SignUpServiceDaodConfig) : SignUpApi {
    private val mailer = config.mailer
    private val verificationUrl = config.verificationUrl
    private val candidates = config.database.get<Candidate>()

    fun copy(verificationLink: String) = SignUpServiceDaod(config.copy(verificationLink))

    companion object {
        fun generateToken() = buildString {
            val N = 4
            repeat(N) {
                append(Random.nextLong(1_000_000_000, 9_999_999_999).toString(radix = 36))
                if (it != N - 1) append("-")
            }
        }
    }

    override fun signUp(params: SignUpParams): Later<SignUpParams> {
        val candidate = Candidate(name = params.name, email = params.email, token = generateToken())
        return candidates.create(candidate).then {
            params
        }
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> {
        val query = find(Candidate::token isEqualTo params.token)
        return candidates.execute(query).then {
            it.firstOrNull() ?: throw Exception("There is no registration candidate with token=${params.token}")
        }.andThen {
            candidates.update(it.copy(status = Status.Verified()))
        }.then {
            params
        }
    }

    override fun sendVerificationLink(email: String): Later<String> {
        val query = find(Candidate::email isEqualTo email)
        return candidates.execute(query).then {
            it.firstOrNull() ?: throw Exception("Candidate with email $email has not yet registered")
        }.andThen {
            val draft = EmailDraft(
                subject = "Account Verification",
                body = "Click here [$verificationUrl?token=${it.token}] to verify your account"
            )
            mailer.send(draft, from = "service@test.com", to = email)
        }.then {
            email
        }
    }
}