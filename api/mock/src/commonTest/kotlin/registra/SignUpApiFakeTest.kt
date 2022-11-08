package registra

import bitframe.ApiConfigMock
import expect.expect
import mailer.MockMailer
import kotlin.test.Test

class SignUpApiFakeTest : AbstractSignUpServiceTest(api, mailer) {
    companion object {
        val mailer = MockMailer()
        val api = SignUpApiFake(
            ApiConfigMock(mailer = mailer)
        )
    }

    @Test
    fun should_be_using_a_signup_api_fake() {
        expect(api.toString()).toBe("SignUpApiFake")
    }
}