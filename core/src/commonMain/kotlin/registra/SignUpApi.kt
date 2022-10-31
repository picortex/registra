@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import koncurrent.Later
import registra.params.SignUpParams
import registra.params.VerificationLinkParams
import registra.params.VerificationParams
import kotlin.js.JsExport

interface SignUpApi {
    fun signUp(params: SignUpParams): Later<SignUpParams>
    fun verify(params: VerificationParams): Later<SignUpParams>
    fun sendVerificationLink(params: VerificationLinkParams): Later<SignUpParams>
}