package registra

import bitframe.Action
import bitframe.Module
import bitframe.ServerConfig
import bitframe.http.HttpResponse
import bitframe.http.HttpRoute
import bitframe.http.compulsoryBody
import bitframe.post
import io.ktor.http.*
import koncurrent.later

import registra.params.SignUpParams
import registra.params.VerificationParams
import registra.payloads.SendVerificationLinkPayload

class RegistraModule(private val config: ServerConfig<SignUpServiceDaod, RegistraEndpoint>) : Module {
    override val name: String = "Registration"

    private val service get() = config.service
    private val endpoint get() = config.endpoint
    private val codec get() = config.codec

    override val actions: List<Action> = listOf(
        post(endpoint.signUp) { req ->
            config.executor.later {
                val body = req.compulsoryBody()
                codec.decodeFromString(SignUpParams.serializer(), body)
            }.andThen {
                service.signUp(it)
            }.then {
                HttpResponse(status = HttpStatusCode.OK, body = req.compulsoryBody())
            }
        },
        post(endpoint.verify) { req ->
            config.executor.later {
                codec.decodeFromString(VerificationParams.serializer(), req.compulsoryBody())
            }.andThen {
                service.verify(it)
            }.then {
                HttpResponse(status = HttpStatusCode.OK, body = req.compulsoryBody())
            }
        },
        post(endpoint.sendVerificationLink) { req ->
            config.executor.later {
                val body = req.compulsoryBody()
                codec.decodeFromString(SendVerificationLinkPayload.serializer(), body)
            }.andThen {
                val s = service.copy(verificationLink = it.url)
                s.sendVerificationLink(it.email)
            }.then {
                HttpResponse(status = HttpStatusCode.OK, body = req.compulsoryBody())
            }
        }
    )
}