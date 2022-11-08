package registra

import expect.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import registra.params.SignUpParams
import registra.params.VerificationLinkParams
import registra.params.VerificationParams
import kotlin.test.Test

abstract class AbstractSignUpApiTest(private val api: SignUpApi) {

    @Test
    fun should_be_able_to_sign_up() = runTest {
        val params = SignUpParams(
            name = "Anderson Lameck",
            email = "andy@lamax.com"
        )
        val res = api.signUp(params).await()

        expect(res.email).toBe("andy@lamax.com")
    }

    @Test
    fun should_be_able_to_send_verfication_link_to_an_email() = runTest {
        val params = VerificationLinkParams(
            email = "andy@lamax.com",
            url = "https://registra.com/verify"
        )

        val res = api.sendVerificationLink(params).await()

        expect(res.email).toBe("andy@lamax.com")
    }

    @Test
    fun should_be_able_to_verify_an_account() = runTest {
        val params = VerificationParams(
            email = "andy@lamax.com",
            token = "token"
        )
        val res = api.verify(params).await()

        expect(res.email).toBe("andy@lamax.com")
    }
}