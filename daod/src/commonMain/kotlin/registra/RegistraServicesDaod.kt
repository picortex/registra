package registra

class RegistraServicesDaod(config: SignUpServiceDaodConfig) : RegistraServices {
    override val signUp by lazy { SignUpServiceDaod(config) }
}