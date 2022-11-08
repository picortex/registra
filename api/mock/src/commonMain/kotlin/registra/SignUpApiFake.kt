package registra

import bitframe.ApiConfigMock

class SignUpApiFake(config: ApiConfigMock = ApiConfigMock()) : SignUpApiDaod(config) {
    override fun toString(): String = "SignUpApiFake"
}