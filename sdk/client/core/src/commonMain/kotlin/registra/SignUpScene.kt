@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import cinematic.BaseScene
import koncurrent.FailedLater
import koncurrent.Later
import symphony.toSubmitConfig
import symphony.toForm
import kotlin.js.JsExport

class SignUpScene(private val config: RegistraScopeConfig<SignUpApi>) : BaseScene() {

    private val api get() = config.api

    private val cache get() = config.cache

    val form = SignUpFields().toForm(
        heading = "Create an account",
        details = "Signup in less than two minutes",
        config = config.toSubmitConfig()
    ) {
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
        val email = form.fields.email.output ?: return FailedLater(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email)
    }
}