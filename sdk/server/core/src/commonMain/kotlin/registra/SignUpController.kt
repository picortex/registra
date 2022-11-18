package registra

import bitframe.ControllerConfig
import bitframe.http.HttpRequest
import bitframe.http.HttpResponse
import bitframe.http.compulsoryBody
import io.ktor.http.HttpStatusCode
import koncurrent.Later
import registra.params.SignUpParams
import registra.params.VerificationParams
import registra.payloads.SendVerificationLinkPayload

class SignUpController(private val config: ControllerConfig<SignUpServiceDaod>) {

    private val codec get() = config.codec

    private val service get() = config.service

    fun signUp(req: HttpRequest): Later<HttpResponse> {
        val body = req.compulsoryBody()
        val params = codec.decodeFromString(SignUpParams.serializer(), body)
        return service.signUp(params).then {
            HttpResponse(status = HttpStatusCode.OK, body = body)
        }
    }

    fun verify(req: HttpRequest): Later<HttpResponse> {
        val body = req.compulsoryBody()
        val params = codec.decodeFromString(VerificationParams.serializer(), req.compulsoryBody())
        return service.verify(params).then {
            HttpResponse(status = HttpStatusCode.OK, body = body)
        }
    }

    fun sendVerificationLink(req: HttpRequest): Later<HttpResponse> {
        val body = req.compulsoryBody()
        val params = codec.decodeFromString(SendVerificationLinkPayload.serializer(), body)
        return service.sendVerificationLink(params.email).then {
            HttpResponse(status = HttpStatusCode.OK, body = body)
        }
    }
}