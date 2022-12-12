package registra

import bitframe.Session
import cache.Cache
import events.EventBus
import koncurrent.Executor
import kotlinx.serialization.StringFormat
import live.MutableLive
import logging.Logger
import registra.internal.SignUpApiRestConfigImpl

val SIGN_UP_API_REST_CONFIG_DEFAULT: SignUpApiRestConfig<Any, Any> = SignUpApiRestConfigImpl

@PublishedApi
internal val DEFAULT = SIGN_UP_API_REST_CONFIG_DEFAULT

inline fun <E, H> SignUpApiRestConfig(
    appId: String = DEFAULT.appId,
    session: MutableLive<Session> = DEFAULT.session,
    cache: Cache = DEFAULT.cache,
    bus: EventBus = DEFAULT.bus,
    logger: Logger = DEFAULT.logger,
    executor: Executor = DEFAULT.executor,
    endpoint: E,
    codec: StringFormat = DEFAULT.codec,
    http: H,
    verificationUrl: String = DEFAULT.verificationUrl,
): SignUpApiRestConfig<E, H> = SignUpApiRestConfigImpl(appId, session, cache, bus, logger, executor, endpoint, codec, http, verificationUrl)