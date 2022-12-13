import expect.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import registra.params.SignUpParams
import registra.params.VerificationParams
import registra.signup.SignupApiPiOneKtor
import kotlin.test.Test

class SignUpApiPiOneTest {

    val api = SignupApiPiOneKtor(SignUpApiPiOneTestConfig())

    @Test
    fun should_easily_signup() = runTest {
        val params = SignUpParams(name = "George Corp", email = "george.sechu@gmail.com")
        val res = api.signUp(params).await()
        expect(res.name).toBe("George Corp")
    }

    @Test
    fun should_send_verification_link() = runTest {
        val res = api.sendVerificationLink("george.sechu@gmail.com").await()
        expect(res).toBe("https://myapp/me/verify")
    }

    @Test
    fun should_verify_email() = runTest {
        val params = VerificationParams(token = "662d31fa-819e-4f36-96b0-9ac36321a2a1", email = "george.sechu@gmail.com")
        val res = api.verify(params).await()
        expect(res.token).toBeNonNull()
    }
}