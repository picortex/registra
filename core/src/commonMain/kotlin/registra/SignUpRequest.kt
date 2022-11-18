@file:JsExport

package registra

import kotlin.js.JsExport

data class SignUpRequest(
    val name: String,
    val email: String,
)