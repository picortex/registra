@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cache.load
import cache.save
import koncurrent.Later
import presenters.forms.toFormConfig
import registra.params.SignUpParams
import registra.params.VerificationLinkParams
import viewmodel.BaseViewModel
import kotlin.js.JsExport

class SignUpViewModel(private val config: SignUpScopeConfig) : BaseViewModel(config) {

    private val api get() = config.api

    private val cache get() = config.cache

    val form = SignUpForm(config = config.toFormConfig()) {
        onSubmit { params ->
            cache.save(SIGN_UP_CACHE_KEY, params).andThen {
                api.signUp(params)
            }.andThen {
                api.sendVerificationLink(params.email.toLinkParams())
            }
        }
    }

    fun restorePreviousSession() = cache.load<SignUpParams>(SIGN_UP_CACHE_KEY).then {
        form.fields.apply {
            email.value = it.email
            name.value = it.name
        }
    }

    fun resendVerificationLink(): Later<VerificationLinkParams> {
        val email = form.fields.email.value ?: return Later.reject(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email.toLinkParams())
    }

    /**
     * Converts the email to [VerificationLinkParams] params
     */
    private fun String.toLinkParams() = VerificationLinkParams(
        email = this,
        url = config.verificationUrl
    )

    companion object {
        private const val SIGN_UP_CACHE_KEY = "registra.sign.up"
    }
}