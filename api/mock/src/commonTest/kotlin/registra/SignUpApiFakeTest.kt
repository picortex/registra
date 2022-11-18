package registra

import bitframe.DaoFactory
import bitframe.DaoFactoryMock
import bitframe.Session
import cache.Cache
import cache.CacheMock
import events.EventBus
import events.InMemoryEventBus
import expect.expect
import koncurrent.Executor
import koncurrent.MockExecutor
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import live.MutableLive
import live.mutableLiveOf
import logging.ConsoleAppender
import logging.Logger
import mailer.Mailer
import mailer.MockMailer
import mailer.MockMailerConfig
import registra.internal.SignUpApiFakeConfigImpl
import kotlin.test.Test

class SignUpApiFakeTest : AbstractSignUpServiceTest(api, testMailer) {
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