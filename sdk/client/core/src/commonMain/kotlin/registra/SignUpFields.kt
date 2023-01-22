@file:JsExport

package registra

import presenters.Fields
import presenters.email
import presenters.text
import kotlin.js.JsExport
import registra.params.SignUpParams as Params

class SignUpFields : Fields() {

    val name = text(
        name = Params::name,
        label = "Name",
        isRequired = true,
        hint = "Enter your personal name"
    )

    val email = email(
        name = Params::email,
        label = "Email Address",
        hint = "Enter your email",
        isRequired = true
    )
}