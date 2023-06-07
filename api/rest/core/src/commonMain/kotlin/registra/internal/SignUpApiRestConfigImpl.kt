package registra.internal

import bitframe.API_CONFIG_REST_DEFAULT
import bitframe.Session
import keep.Cache
import events.EventBus
import koncurrent.Executor
import kotlinx.serialization.StringFormat
import cinematic.MutableLive
import lexi.Logger
import registra.SignUpApiRestConfig

@PublishedApi
internal class SignUpApiRestConfigImpl<out E, out H>(
    override val appId: String,
    override val session: MutableLive<Session>,
    override val cache: Cache,
    override val bus: EventBus,
    override val logger: Logger,
    override val executor: Executor,
    override val endpoint: E,
    override val codec: StringFormat,
    override val http: H,
    override val verificationUrl: String
) : SignUpApiRestConfig<E, H> {
    override fun <E2> map(transform: (E) -> E2) = SignUpApiRestConfigImpl(appId, session, cache, bus, logger, executor, transform(endpoint), codec, http, verificationUrl)

    companion object Default : SignUpApiRestConfig<Any, Any> by SignUpApiRestConfigImpl(
        appId = API_CONFIG_REST_DEFAULT.appId,
        session = API_CONFIG_REST_DEFAULT.session,
        cache = API_CONFIG_REST_DEFAULT.cache,
        bus = API_CONFIG_REST_DEFAULT.bus,
        logger = API_CONFIG_REST_DEFAULT.logger,
        executor = API_CONFIG_REST_DEFAULT.executor,
        endpoint = API_CONFIG_REST_DEFAULT.endpoint,
        codec = API_CONFIG_REST_DEFAULT.codec,
        http = API_CONFIG_REST_DEFAULT.http,
        verificationUrl = "https://default.app.tz/verify",
    )
}