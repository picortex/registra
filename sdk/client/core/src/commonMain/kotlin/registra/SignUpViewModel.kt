@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import koncurrent.Later
import koncurrent.FailedLater
import presenters.forms.toFormConfig
import viewmodel.BaseViewModel
import viewmodel.ScopeConfig
import kotlin.js.JsExport

class SignUpViewModel(private val config: ScopeConfig<SignUpApi>) : BaseViewModel(config) {

    private val api get() = config.api

    private val cache get() = config.cache

    val form = SignUpForm(config = config.toFormConfig()) {
        onSubmit { params ->
            cache.save(params).andThen {
                api.signUp(params)
            }.andThen {
                api.sendVerificationLink(params.email)
            }
        }
    }

    fun restorePreviousSession() = cache.loadSignUpParams().then {
        form.fields.apply {
            email.set(it.email)
            name.set(it.name)
        }
    }

    fun resendVerificationLink(): Later<String> {
        val email = form.fields.email.output.value ?: return FailedLater(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email)
    }
}