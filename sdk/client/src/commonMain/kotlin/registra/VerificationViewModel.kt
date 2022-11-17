@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cache.load
import cache.save
import koncurrent.Later
import koncurrent.later.catch
import presenters.states.Failure
import presenters.states.LazyState
import presenters.states.Loading
import presenters.states.Pending
import presenters.states.Success
import registra.RegistraCacheKeys.SIGN_UP_CACHE_KEY
import registra.params.SignUpParams
import registra.params.VerificationParams
import viewmodel.ScopeConfig
import viewmodel.ViewModel
import kotlin.js.JsExport

class VerificationViewModel(
    private val config: ScopeConfig<SignUpApi>
) : ViewModel<LazyState<VerificationParams>>(config.of(Pending)) {

    private val api get() = config.api

    fun verify(link: String): Later<Any> = config.cache.load<SignUpParams>(SIGN_UP_CACHE_KEY).andThen { params ->
        ui.value = Loading(message = "Verifying your account (${params.email}), please wait . . . ")
        parseToken(link).andThen { api.verify(VerificationParams(params.email, it)) }
    }.andThen {
        config.cache.save(RegistraCacheKeys.REGISTRA_PARAMS_KEY, it)
    }.then { params ->
        ui.value = Success(params)
    }.catch {
        ui.value = Failure(cause = it) {
            onRetry { verify(link) }
        }
    }

    companion object {
        internal fun parseToken(link: String): Later<String> {
            val residue = link.split("?").getOrNull(1) ?: return Later.reject(QUERY_PARAMS_NOT_PROVIDED)

            val queryParams = residue.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val token = queryParams["token"] ?: return Later.reject(TOKEN_NOT_FOUND_IN_LINK)
            return Later.resolve(token)
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain token to verify")
    }
}