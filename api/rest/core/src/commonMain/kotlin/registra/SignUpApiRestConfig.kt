package registra

import bitframe.ApiConfigRest

interface SignUpApiRestConfig<out E, out H> : ApiConfigRest<E, H>, SignUpServiceConfig {
    override fun <E2> map(transformer: (E) -> E2): SignUpApiRestConfig<E2, H>
}