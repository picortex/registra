package registra.signup

import bitframe.ApiConfigKtor
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
import registra.params.SignUpParams
import registra.params.VerificationLinkParams
import registra.params.VerificationParams

class SignupApiPiOneKtor(override val config: ApiConfigKtor<PiOneEndpoint>) : PiOneApi by PiOneApi(config), SignUpApi {

    override fun signUp(params: SignUpParams): Later<SignUpParams> = config.scope.later {
        val response = client.post(config.endpoint.signup) {
            setBody(
                codec.encodeToString(
                    PiOneUnAuthorized(body = mapOf(
                        "email" to params.email,
                        "name" to params.name
                    ))
                )
            )
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
        val response = client.post(config.endpoint.verifyEmail) {
            setBody(
                codec.encodeToString(
                    PiOneUnAuthorized(body = mapOf(
                        "email" to params.email,
                        "token" to params.token
                    ))
                )
            )
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)

        if (result["status"]?.jsonPrimitive?.content == "ok") {
            params
        } else {
            throw RuntimeException(result["error"]?.jsonPrimitive?.content)
        }
    }

    override fun sendVerificationLink(params: VerificationLinkParams): Later<VerificationLinkParams> = config.scope.later {
        val response = client.post(config.endpoint.sendVerificationLink) {
            setBody(
                codec.encodeToString(
                    PiOneUnAuthorized(body = mapOf(
                        "email" to params.email,
                        "url" to params.url
                    ))
                )
            )
        }

        val text = response.bodyAsText()
        val result = codec.decodeFromString(JsonObject.serializer(), text)

        if (result["status"]?.jsonPrimitive?.content == "ok") {
            params
        } else {
            throw RuntimeException(result["error"]?.jsonPrimitive?.content)
        }
    }
}