package registra

import bitframe.Session
import cache.Cache
import events.EventBus
import io.ktor.client.*
import koncurrent.CoroutineExecutor
import kotlinx.serialization.StringFormat
import cinematic.MutableLive
import logging.Logger
import registra.internal.SignUpApiRestKtorConfigImpl

val SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT: SignUpApiRestKtorConfig<Any> = SignUpApiRestKtorConfigImpl

inline fun <E> SignUpApiRestKtorConfig(
    appId: String = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.appId,
    session: MutableLive<Session> = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.session,
    cache: Cache = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.cache,
    bus: EventBus = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.bus,
    logger: Logger = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.logger,
    executor: CoroutineExecutor = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.executor,
    endpoint: E,
    codec: StringFormat = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.codec,
    http: HttpClient = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.http,
    verificationUrl: String = SIGN_UP_API_REST_KTOR_CONFIG_DEFAULT.verificationUrl,
): SignUpApiRestKtorConfig<E> = SignUpApiRestKtorConfigImpl(appId, session, cache, bus, logger, executor, endpoint, codec, http, verificationUrl)