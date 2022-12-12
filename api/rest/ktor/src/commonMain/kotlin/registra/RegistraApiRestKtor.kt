package registra

class RegistraApiRestKtor(config: SignUpApiRestKtorConfig<RegistraEndpoint>) : RegistraApi {
    override val signUp: SignUpApi by lazy { SignUpApiRestKtor(config) }
}