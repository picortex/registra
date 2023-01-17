@file:JsExport

package registra

import kotlin.js.JsExport

interface RegistraScope {
    val signUp: SignUpViewModel
    val verification: VerificationViewModel
}