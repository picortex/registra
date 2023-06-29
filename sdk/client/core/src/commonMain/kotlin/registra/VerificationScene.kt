@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cinematic.LazyScene
import kase.Loading
import kase.Pending
import kase.toLazyState
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import registra.params.VerificationParams
import kotlin.js.JsExport

class VerificationScene(
    private val config: RegistraScopeConfig<SignUpApi>
) : LazyScene<VerificationParams>(Pending) {

    private val api = config.api

    private val cache = config.cache

    fun verify(link: String): Later<Any> = cache.loadSignUpParams().andThen { params ->
        ui.value = Loading(message = "Verifying your account (${params.email}), please wait . . . ")
        parseToken(link).andThen { api.verify(VerificationParams(params.email ?: "", it)) }
    }.andThen {
        cache.save(it)
    }.finally {
        ui.value = it.toLazyState { onRetry { verify(link) } }
    }

    companion object {
        internal fun parseToken(link: String): Later<String> {
            val residue = link.split("?").getOrNull(1) ?: return FailedLater(TOKEN_NOT_FOUND_IN_LINK)

            val queryParams = residue.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val token = queryParams["token"] ?: return FailedLater(TOKEN_NOT_FOUND_IN_LINK)
            return Later(token)
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain verification token")
    }
}