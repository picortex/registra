@file:JsExport

package registra.params

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class SignUpParams(
    var name: String?,
    var email: String?,
)