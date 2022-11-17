@file:JsExport

package registra.params

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class VerificationParams(
    val email: String,
    val token: String
)