@file:JsExport

package registra

import kotlin.js.JsExport

data class Product(
    val code: String,
    val name: String,
    val logo: String?
)