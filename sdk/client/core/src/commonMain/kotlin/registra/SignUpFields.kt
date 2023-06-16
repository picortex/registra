@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import symphony.Fields
import symphony.email
import symphony.text
import kotlin.js.JsExport
import registra.params.SignUpParams as Params

class SignUpFields : Fields<Params>(Params("", "")) {

    val name = text(
        name = output::name,
        label = "Name",
        isRequired = true,
        hint = "Enter your personal name"
    )

    val email = email(
        name = output::email,
        label = "Email Address",
        hint = "Enter your email",
        isRequired = true
    )
}