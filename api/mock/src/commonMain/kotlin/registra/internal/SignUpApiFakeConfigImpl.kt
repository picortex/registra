package registra.internal

import bitframe.DaoFactory
import bitframe.DaoFactoryMock
import bitframe.Session
import cache.Cache
import cache.CacheMock
import events.EventBus
import events.InMemoryEventBus
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
import registra.SignUpApiFakeConfig

data class SignUpApiFakeConfigImpl(
    override val appId: String = "TEST",
    override val session: MutableLive<Session> = mutableLiveOf(Session.Unknown),
    override val cache: Cache = CacheMock(),
    override val bus: EventBus = InMemoryEventBus(),
    override val logger: Logger = Logger(ConsoleAppender()),
    override val executor: Executor = MockExecutor(name = "test executor"),
    override val codec: StringFormat = Json { },
    override val database: DaoFactory = DaoFactoryMock(),
    override val mailer: Mailer = MockMailer(),
    override val verificationUrl: String = "https://app.test.com",
) : SignUpApiFakeConfig {
    override fun copy(verificationLink: String) = copy(verificationUrl = verificationUrl)
}
