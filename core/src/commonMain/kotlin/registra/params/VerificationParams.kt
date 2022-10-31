@file:JsExport

package registra.params

import kotlin.js.JsExport

data class VerificationParams(
    val email: String,
    val token: String
)