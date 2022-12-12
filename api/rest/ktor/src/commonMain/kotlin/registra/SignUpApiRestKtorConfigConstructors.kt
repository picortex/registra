package registra

import bitframe.Session
import cache.Cache
import events.EventBus
import io.ktor.client.*
import koncurrent.CoroutineExecutor
import kotlinx.serialization.StringFormat
import live.MutableLive
import logging.Logger
import registra.internal.SignUpApiRestKtorConfigImpl

val SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT: SignUpApiRestKtorConfig<Any> = SignUpApiRestKtorConfigImpl

@PublishedApi
internal val DEFAULT = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT

inline fun <E> SignUpApiRestKtorConfig(
    appId: String = DEFAULT.appId,
    session: MutableLive<Session> = DEFAULT.session,
    cache: Cache = DEFAULT.cache,
    bus: EventBus = DEFAULT.bus,
    logger: Logger = DEFAULT.logger,
    executor: CoroutineExecutor = DEFAULT.executor,
    endpoint: E,
    codec: StringFormat = DEFAULT.codec,
    http: HttpClient = DEFAULT.http,
    verificationUrl: String = DEFAULT.verificationUrl,
): SignUpApiRestKtorConfig<E> = SignUpApiRestKtorConfigImpl(appId, session, cache, bus, logger, executor, endpoint, codec, http, verificationUrl)