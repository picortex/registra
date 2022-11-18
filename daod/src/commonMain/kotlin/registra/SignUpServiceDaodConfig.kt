package registra

import bitframe.ServiceConfigDaod

interface SignUpServiceDaodConfig : ServiceConfigDaod, SignUpApiConfig {
    fun copy(verificationLink: String): SignUpServiceDaodConfig
}