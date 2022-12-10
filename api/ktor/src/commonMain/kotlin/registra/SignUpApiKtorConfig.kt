package registra

import bitframe.ApiConfigKtor

interface SignUpApiKtorConfig<out E> : ApiConfigKtor<E>, SignUpServiceConfig {
    fun <R> map(transformer: (E) -> R): SignUpApiKtorConfig<R>
}