import bitframe.Session
import cache.CacheMock
import cache.CacheMockConfig
import events.InMemoryEventBus
import io.ktor.client.HttpClient
import koncurrent.CoroutineExecutor
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import live.MutableLive
import live.mutableLiveOf
import logging.ConsoleAppender
import logging.Logger
import pione.PiOneEndpoint
import registra.pione.API_URL
import registra.signup.SignUpApiConfigKtor

fun SignUpApiPiOneTestConfig(link: String = API_URL) = object : SignUpApiConfigKtor<PiOneEndpoint> {
    val url = link.ifEmpty { PiOneEndpoint.DEFAULT_STAGING_ENDPOINT }
    override val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineName("registra-test-scope"))
    override val executor = CoroutineExecutor(scope)
    override val http = HttpClient { }
    override val appId = "<test>"
    override val session: MutableLive<Session> = mutableLiveOf(Session.Unknown)
    override val cache = CacheMock(CacheMockConfig(executor = executor))
    override val bus = InMemoryEventBus()
    override val logger = Logger(ConsoleAppender())
    override val endpoint = PiOneEndpoint(url)
    override val codec = Json { }
    override val verificationUrl = "https://test.app/verify"
}