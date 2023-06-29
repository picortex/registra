package registra

import expect.expect
import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import mailer.MockMailer
import registra.params.SignUpParams
import registra.params.VerificationParams
import kotlin.test.Test
import kotlin.test.fail

abstract class AbstractSignUpServiceTest(val api: SignUpApi, val mailer: MockMailer) {

    @Test
    fun should_be_able_to_sign_up_with_email_verification() = runTest {
        val name = "John Doe"
        val email = "john@doe.com"

        api.signUp(SignUpParams(name, email)).await()

        api.sendVerificationLink(email).await()

        val sentMail = mailer.outbox[email]?.firstOrNull() ?: fail("verification was not sent to $email")

        val url = sentMail.body.extractUrl()
        val token = url.extractToken()

        val res = api.verify(VerificationParams(email, token)).await()

        expect<String>(res.email).toBe(email)
        expect(res.token).toBe(token)
    }

    @Test
    fun should_be_able_to_sign_up_without_email_verification() = runTest {
        val name = "Jane Doe"
        val email = "jane@doe.com"

        val res = api.signUp(SignUpParams(name, email)).await()

        expect(res.email).toBe(email)
    }

    private fun String.extractUrl() = substringAfter("[").substringBefore("]")

    private fun String.extractToken() = substringAfter("=")
}