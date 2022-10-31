package flame

import bitframe.ApiConfigKtor
import flame.customers.CustomersApiPiOneKtor
import pione.PiOneEndpoint

class FlameApiPiOne(private val config: ApiConfigKtor<PiOneEndpoint>) : FlameApi {
    override val customers by lazy { CustomersApiPiOneKtor(config) }
}