@file:JsExport

package registra

import hormone.HasApi
import keep.Cacheable
import lexi.Logable
import kotlin.js.JsExport

interface RegistraScopeConfig<out A> : HasApi<A>, Logable, Cacheable {
    override fun <R> map(transformer: (A) -> R): RegistraScopeConfig<R>
}