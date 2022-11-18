package registra

import expect.expect
import mailer.MockMailer
import mailer.MockMailerConfig
import registra.internal.SignUpApiFakeConfigImpl
import kotlin.test.Test

class SignUpApiFakeTest : AbstractSignUpServiceTest(api, mailer) {
    companion object {
        private val mailerConfig = MockMailerConfig(
            marginWidth = "\t".repeat(3),
            charsPerLine = 95
        )
        private val mailer = MockMailer(mailerConfig)
        val config = SignUpApiFakeConfigImpl(mailer = mailer)
        val api = SignUpApiFake(config)
    }

    @Test
    fun should_be_using_a_signup_api_fake() {
        expect(api.toString()).toBe("SignUpApiFake")
    }
}