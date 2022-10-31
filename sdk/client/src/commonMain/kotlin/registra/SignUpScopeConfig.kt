@file:JsExport

package registra

import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface SignUpScopeConfig : ScopeConfig<SignUpApi> {
    val verificationUrl: String
}