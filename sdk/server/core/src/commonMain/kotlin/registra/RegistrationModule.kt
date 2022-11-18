package registra

import bitframe.ModuleConfig
import bitframe.Action
import bitframe.Module
import bitframe.http.HttpRoute
import io.ktor.http.HttpMethod
import koncurrent.later.await

class RegistrationModule(private val config: ModuleConfig<SignUpController, RegistraEndpoint>) : Module {
    override val name: String = "Registration"

    private val controller get() = config.controller
    private val endpoint get() = config.endpoint

    override val actions: List<Action> = listOf(
        Action("sign up", mapOf(), HttpRoute(HttpMethod.Post, endpoint.signUp) {
            controller.signUp(it).await()
        }),
        Action("verify", mapOf(), HttpRoute(HttpMethod.Post, endpoint.verify) {
            controller.verify(it).await()
        }),
        Action("send verification link", mapOf(), HttpRoute(HttpMethod.Post, endpoint.verify) {
            controller.sendVerificationLink(it).await()
        })
    )
}