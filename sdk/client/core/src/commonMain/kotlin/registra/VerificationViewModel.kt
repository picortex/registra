@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import kase.Failure
import kase.Loading
import kase.Success
import koncurrent.FailedLater
import koncurrent.Later
import registra.params.VerificationParams
import viewmodel.LazyViewModel
import viewmodel.ScopeConfig
import kotlin.js.JsExport

class VerificationViewModel(
    private val config: ScopeConfig<SignUpApi>
) : LazyViewModel<VerificationParams>(config) {

    private val api = config.api

    private val cache = config.cache

    fun verify(link: String): Later<Any> = cache.loadSignUpParams().andThen { params ->
        ui.value = Loading(message = "Verifying your account (${params.email}), please wait . . . ")
        parseToken(link).andThen { api.verify(VerificationParams(params.email, it)) }
    }.andThen {
        cache.save(it)
    }.then { params ->
        ui.value = Success(params)
    }.catch {
        ui.value = Failure(cause = it) {
            onRetry { verify(link) }
        }
    }

    companion object {
        internal fun parseToken(link: String): Later<String> {
            val residue = link.split("?").getOrNull(1) ?: return FailedLater(QUERY_PARAMS_NOT_PROVIDED)

            val queryParams = residue.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val token = queryParams["token"] ?: return FailedLater(TOKEN_NOT_FOUND_IN_LINK)
            return Later(token)
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain token to verify")
    }
}