@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cinematic.BaseScene
import koncurrent.Later
import koncurrent.FailedLater
import symphony.toFormConfig
import kotlin.js.JsExport

class SignUpScene(private val config: RegistraScopeConfig<SignUpApi>) : BaseScene() {

    private val api get() = config.api

    private val cache get() = config.cache

    val form = SignUpForm(config = config.toFormConfig(exitOnSubmitted = false)) {
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
        val email = form.fields.email.data.value.output ?: return FailedLater(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email)
    }
}