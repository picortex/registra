package registra

import bitframe.ServiceConfigDaod

interface SignUpServiceDaodConfig : ServiceConfigDaod, SignUpServiceConfig {
    fun copy(verificationUrl: String): SignUpServiceDaodConfig
}
