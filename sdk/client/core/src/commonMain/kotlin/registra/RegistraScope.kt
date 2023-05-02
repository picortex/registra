@file:JsExport

package registra

import kotlin.js.JsExport

interface RegistraScope {
    val signUp: SignUpScene
    val verification: VerificationScene
}