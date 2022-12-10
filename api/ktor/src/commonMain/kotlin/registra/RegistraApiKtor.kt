package registra

class RegistraApiKtor(config: SignUpApiKtorConfig<RegistraEndpoint>) : RegistraApi {
    override val signUp: SignUpApi by lazy { SignUpApiKtor(config) }
}