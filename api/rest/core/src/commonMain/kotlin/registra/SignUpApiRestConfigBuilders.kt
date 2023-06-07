package registra

import bitframe.Session
import keep.Cache
import events.EventBus
import koncurrent.Executor
import kotlinx.serialization.StringFormat
import cinematic.MutableLive
import lexi.Logger
import registra.internal.SignUpApiRestConfigImpl

val SIGN_UP_API_REST_CONFIG_DEFAULT: SignUpApiRestConfig<Any, Any> = SignUpApiRestConfigImpl

inline fun <E, H> SignUpApiRestConfig(
    appId: String = SIGN_UP_API_REST_CONFIG_DEFAULT.appId,
    session: MutableLive<Session> = SIGN_UP_API_REST_CONFIG_DEFAULT.session,
    cache: Cache = SIGN_UP_API_REST_CONFIG_DEFAULT.cache,
    bus: EventBus = SIGN_UP_API_REST_CONFIG_DEFAULT.bus,
    logger: Logger = SIGN_UP_API_REST_CONFIG_DEFAULT.logger,
    executor: Executor = SIGN_UP_API_REST_CONFIG_DEFAULT.executor,
    endpoint: E,
    codec: StringFormat = SIGN_UP_API_REST_CONFIG_DEFAULT.codec,
    http: H,
    verificationUrl: String = SIGN_UP_API_REST_CONFIG_DEFAULT.verificationUrl,
): SignUpApiRestConfig<E, H> = SignUpApiRestConfigImpl(appId, session, cache, bus, logger, executor, endpoint, codec, http, verificationUrl)