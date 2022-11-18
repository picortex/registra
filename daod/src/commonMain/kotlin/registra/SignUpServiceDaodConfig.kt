package registra

import bitframe.ServiceConfigDaod

interface SignUpServiceDaodConfig : ServiceConfigDaod, SignUpApiConfig {
    fun copy(verificationUrl: String): SignUpServiceDaodConfig
}
