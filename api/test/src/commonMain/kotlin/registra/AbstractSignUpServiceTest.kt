package registra

import expect.expect
import expect.toContain
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import mailer.MockMailer
import registra.params.VerificationLinkParams
import kotlin.test.Test

abstract class AbstractSignUpServiceTest(val api: SignUpApi, val mailer: MockMailer) : AbstractSignUpApiTest(api) {

    @Test
    fun should_send_verfication_link_through_an_email() = runTest {
        val params = VerificationLinkParams(
            email = "andy@lamax.com",
            url = "https://registra.com/verify"
        )

        val res = api.sendVerificationLink(params).await()

        expect(res.email).toBe("andy@lamax.com")

        val mails = mailer.outbox["andy@lamax.com"]
        expect(mails).toContainElements()

        expect(mails?.getOrNull(0)?.body).toContain("https://registra.com/verify")
    }
}