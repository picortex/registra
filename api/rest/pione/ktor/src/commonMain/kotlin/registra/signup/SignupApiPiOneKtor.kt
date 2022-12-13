package registra.signup

import io.ktor.client.request.*
import io.ktor.client.statement.*
import koncurrent.Later
import koncurrent.later
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import pione.PiOneApi
import pione.PiOneEndpoint
import pione.PiOneUnAuthorized
import registra.SignUpApi
import registra.SignUpApiRestKtorConfig
import registra.params.SignUpParams
import registra.params.VerificationParams

class SignupApiPiOneKtor(override val config: SignUpApiRestKtorConfig<PiOneEndpoint>) : PiOneApi by PiOneApi(config), SignUpApi {

    override fun signUp(params: SignUpParams): Later<SignUpParams> = config.scope.later {
        val payload = codec.encodeToString(PiOneUnAuthorized(body = mapOf("email" to params.email, "name" to params.name)))
        val response = client.post(config.endpoint.signup) {
            setBody(payload)
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)

        if (result["status"]?.jsonPrimitive?.content == "ok") {
            params
        } else {
            throw RuntimeException(result["error"]?.jsonPrimitive?.content)
        }
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> = config.scope.later {
        val payload = codec.encodeToString(PiOneUnAuthorized(body = mapOf("email" to params.email, "token" to params.token)))
        val response = client.post(config.endpoint.verifyEmail) {
            setBody(payload)
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)

        if (result["status"]?.jsonPrimitive?.content == "ok") {
            params
        } else {
            throw RuntimeException(result["error"]?.jsonPrimitive?.content)
        }
    }

    override fun sendVerificationLink(email: String): Later<String> = config.scope.later {
        val payload = codec.encodeToString(PiOneUnAuthorized(body = mapOf("email" to email, "url" to config.verificationUrl)))
        val response = client.post(config.endpoint.sendVerificationLink) {
            setBody(payload)
        }
        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)

        if (result["status"]?.jsonPrimitive?.content == "ok") {
            email
        } else {
            throw RuntimeException(result["error"]?.jsonPrimitive?.content)
        }
    }
}