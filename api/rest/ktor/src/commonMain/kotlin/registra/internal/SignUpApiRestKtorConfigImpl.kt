package registra.internal

import bitframe.API_CONFIG_REST_KTOR_DEFAULT
import bitframe.Session
import keep.Cache
import events.EventBus
import io.ktor.client.*
import koncurrent.CoroutineExecutor
import kotlinx.serialization.StringFormat
import cinematic.MutableLive
import lexi.Logger
import registra.SIGN_UP_API_REST_CONFIG_DEFAULT
import registra.SignUpApiRestKtorConfig

@PublishedApi
internal class SignUpApiRestKtorConfigImpl<out E>(
    override val appId: String,
    override val session: MutableLive<Session>,
    override val cache: Cache,
    override val bus: EventBus,
    override val logger: Logger,
    override val executor: CoroutineExecutor,
    override val endpoint: E,
    override val codec: StringFormat,
    override val http: HttpClient,
    override val verificationUrl: String
) : SignUpApiRestKtorConfig<E> {
    override fun <E2> map(transform: (E) -> E2) = SignUpApiRestKtorConfigImpl(appId, session, cache, bus, logger, executor, transform(endpoint), codec, http, verificationUrl)

    companion object Default : SignUpApiRestKtorConfig<Any> by SignUpApiRestKtorConfigImpl(
        appId = SIGN_UP_API_REST_CONFIG_DEFAULT.appId,
        session = SIGN_UP_API_REST_CONFIG_DEFAULT.session,
        cache = SIGN_UP_API_REST_CONFIG_DEFAULT.cache,
        bus = SIGN_UP_API_REST_CONFIG_DEFAULT.bus,
        logger = SIGN_UP_API_REST_CONFIG_DEFAULT.logger,
        executor = API_CONFIG_REST_KTOR_DEFAULT.executor,
        endpoint = SIGN_UP_API_REST_CONFIG_DEFAULT.endpoint,
        codec = SIGN_UP_API_REST_CONFIG_DEFAULT.codec,
        http = API_CONFIG_REST_KTOR_DEFAULT.http,
        verificationUrl = SIGN_UP_API_REST_CONFIG_DEFAULT.verificationUrl,
    )
}