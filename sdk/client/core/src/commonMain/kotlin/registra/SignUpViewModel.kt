@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cache.load
import cache.save
import koncurrent.Later
import presenters.forms.toFormConfig
import registra.params.SignUpParams
import viewmodel.BaseViewModel
import viewmodel.ScopeConfig
import kotlin.js.JsExport

class SignUpViewModel(private val config: ScopeConfig<SignUpApi>) : BaseViewModel(config) {

    private val api get() = config.api

    private val cache get() = config.cache

    val form = SignUpForm(config = config.toFormConfig()) {
        onSubmit { params ->
            cache.save(RegistraCacheKeys.SIGN_UP_CACHE_KEY, params).andThen {
                api.signUp(params)
            }.andThen {
                api.sendVerificationLink(params.email)
            }
        }
    }

    fun restorePreviousSession() = cache.load<SignUpParams>(RegistraCacheKeys.SIGN_UP_CACHE_KEY).then {
        form.fields.apply {
            email.set(it.email)
            name.set(it.name)
        }
    }

    fun resendVerificationLink(): Later<String> {
        val email = form.fields.email.output.value ?: return Later.reject(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email)
    }
}