@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import koncurrent.Later
import koncurrent.later.catch
import presenters.states.Failure
import presenters.states.LazyState
import presenters.states.Loading
import presenters.states.Pending
import presenters.states.Success
import registra.params.SignUpParams
import registra.params.VerificationParams
import viewmodel.ViewModel
import kotlin.js.JsExport

class VerificationViewModel(
    private val config: SignUpScopeConfig
) : ViewModel<LazyState<SignUpParams>>(config.of(Pending)) {

    private val api get() = config.api

    fun verify(link: String): Later<Any> = parseEmailAndToken(link).andThen {
        ui.value = Loading(message = "Verify your account (${it.email}), please wait . . . ")
        // message = "Your account has successfully been created. Click below to log in magically"
        api.verify(it).then { params ->
            ui.value = Success(params)
        }
    }.catch {
        ui.value = Failure(cause = it) {
            onRetry { verify(link) }
        }
    }

    companion object {
        internal fun parseEmailAndToken(link: String): Later<VerificationParams> {
            val residue = link.split("?").getOrNull(1) ?: return Later.reject(QUERY_PARAMS_NOT_PROVIDED)

            val queryParams = residue.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val email = queryParams["email"] ?: return Later.reject(EMAIL_NOT_FOUND_IN_LINK)
            val token = queryParams["token"] ?: return Later.reject(TOKEN_NOT_FOUND_IN_LINK)
            return Later.resolve(VerificationParams(email, token))
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val EMAIL_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain email to verify")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain token to verify")
    }
}