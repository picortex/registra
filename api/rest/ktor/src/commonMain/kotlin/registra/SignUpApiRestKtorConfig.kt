package registra

import bitframe.ApiConfigRestKtor
import io.ktor.client.HttpClient

interface SignUpApiRestKtorConfig<out E> : SignUpApiRestConfig<E, HttpClient>, ApiConfigRestKtor<E> {
    override fun <E2> map(transformer: (E) -> E2): SignUpApiRestKtorConfig<E2>
}