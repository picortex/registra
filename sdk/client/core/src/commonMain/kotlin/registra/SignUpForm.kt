@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package registra

import presenters.forms.Form
import presenters.forms.FormActionsBuildingBlock
import presenters.forms.FormConfig
import kotlin.js.JsExport
import registra.params.SignUpParams as Params

class SignUpForm(
    heading: String = "Create an account",
    details: String = "Signup in less than two minutes",
    config: FormConfig<Params>,
    initializer: FormActionsBuildingBlock<Params, String>
) : Form<SignUpFields, Params, String>(
    heading = heading,
    details = details,
    fields = SignUpFields(),
    config = config,
    initializer = initializer
)