@file:JsExport

package registra

import presenters.forms.Fields
import presenters.forms.fields.email
import presenters.forms.fields.text
import kotlin.js.JsExport
import registra.params.SignUpParams as Params

class SignUpFields : Fields() {

    val name by text(
        name = Params::name,
        label = "Name",
        isRequired = true,
        hint = "Enter your personal name"
    )

    val email by email(
        name = Params::email,
        label = "Email Address",
        hint = "Enter your email",
        isRequired = true
    )
}