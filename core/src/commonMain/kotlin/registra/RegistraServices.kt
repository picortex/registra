@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import registra.params.SignUpParams
import kotlin.js.JsExport
import kotlin.js.JsName

interface RegistraServices {

    @JsName("signUpApi")
    val signUp: SignUpApi

    fun signUp(params: SignUpParams) = signUp.signUp(params)
}